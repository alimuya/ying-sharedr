package com.alimuya.ying.sharedr;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceFileWrapper implements IFileWrapper{
	private static final String DATAFILENAME="test";
	private static final String LOCKNAME="lock";
	private volatile boolean isExpired=true;
	private WatchService watchService;
	private String dirPath;
	
	public WatchServiceFileWrapper(String dirPath) throws IOException{
		this.dirPath=dirPath;
		File dir=new File(dirPath);
		FileUtils.createDir(dir);
		File lockFile=new File(dirPath+"/"+LOCKNAME);
		if(!lockFile.exists()){
			lockFile.createNewFile();
		}
		
		Path path=dir.toPath();
		this.watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE); 
	}
	
	
	
	
	public void watch(){
		watchThread.start();
	}
	private Thread watchThread =new  Thread(){
		
		@Override
		public void run() {
			try {
				 while (!this.isInterrupted()) {  
			            final WatchKey key = watchService.take();  
			            for (WatchEvent<?> watchEvent : key.pollEvents()) {  
			                final Kind<?> kind = watchEvent.kind();  
			                System.out.println(watchEvent.context()+"发生了"+watchEvent.kind()+"事件"); 
			                if (kind == StandardWatchEventKinds.OVERFLOW) { 
			                    continue;  
			                }  
			                if(kind == StandardWatchEventKinds.ENTRY_CREATE){
			                	WatchServiceFileWrapper.this.isExpired=true;
			                }  
			                if(kind == StandardWatchEventKinds.ENTRY_MODIFY){ 
			                	WatchServiceFileWrapper.this.isExpired=true;
			                }  
			                if(kind == StandardWatchEventKinds.ENTRY_DELETE){ 
			                	WatchServiceFileWrapper.this.isExpired=true;
			                }  
			            }  
			            boolean valid = key.reset();  
			            if (!valid) {  
			                break;  
			            }  
			        }  
			} catch (InterruptedException e) {
				try {
					watchService.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	
	public void close(){
		this.watchThread.interrupt();
	}
	
	@Override
	public void write(byte[] bytes,ISwapConflictHandler handler) throws IOException {
		if(isExpired){
			
		}
		RandomAccessFile raf = new RandomAccessFile(dirPath+"/"+LOCKNAME, "rw");   
		FileChannel channel = raf.getChannel();
		FileLock lock=null;
		try{
			lock = channel.lock();
			FileUtils.writeBytesFile(bytes,dirPath+"/"+DATAFILENAME);
		}finally{
			if(lock!=null){
				lock.release();
			}
		}
	}
	
	
	@Override
	public byte[] read() throws IOException {
		RandomAccessFile raf = new RandomAccessFile(dirPath+"/"+LOCKNAME, "rw");   
		FileChannel channel = raf.getChannel();
		FileLock lock=null;
		try{
			lock = channel.lock();
			long version = raf.readLong();
			byte[] result = FileUtils.readBytesFile(dirPath+"/"+DATAFILENAME);
			isExpired=false;
			return result;
		}finally{
			if(lock!=null){
				lock.release();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		File file=new File("");
		String testFileName=file.getAbsolutePath()+"/file";
		WatchServiceFileWrapper sss = new WatchServiceFileWrapper(testFileName);
		sss.watch();
		sss.read();
//		sss.write("ww".getBytes());
		sss.close();
		System.out.println("finish");
	}


	public boolean isExpired() {
		return isExpired;
	}


}
