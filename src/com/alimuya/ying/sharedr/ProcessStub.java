package com.alimuya.ying.sharedr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;

import com.alimuya.ying.sharedr.IFileWatcher.Event;
import com.alimuya.ying.sharedr.IFileWatcher.IFileWatcherListner;


/**
 * @author ov_alimuya
 *
 */
public class ProcessStub {
	private String processName;
	private volatile boolean isFresh=false;
	private File processFile;
	private int pid;
	private final boolean isSelf;
	private IProcessManager processManager;
	private HashMap<String,Object> cache=new HashMap<String,Object>();
	private IFileWatcherListner listner=new IFileWatcherListner() {

		@Override
		public void onChange(String path, Event event) {
			if(!ProcessStub.this.isSelf && path.equals(processFile.getAbsolutePath())){
				ProcessStub.this.isFresh=false;
			}
		}
	};
	
	public  ProcessStub (File dir,String processName){
		String fileName=dir.getAbsoluteFile()+"/"+processName;
		this.processFile=new File(fileName);
		this.processName=processName;
		isSelf=true;
		isFresh=true;
	}

	public ProcessStub (File processFile){
		isSelf=false;
		isFresh=false;
	}
	
	
	public boolean isValid(){
		return false;
	}
	
	public void sync() throws IOException{
		serializing(processFile);
	}
	
	
	private void lockSelfProcessFile() throws IOException{
		final RandomAccessFile raf=new RandomAccessFile(processFile, "rw");
		final FileLock lock = raf.getChannel().lock();
		Runtime.getRuntime().addShutdownHook(new Thread(){

			@Override
			public void run() {
				try {
					lock.release();
					raf.close();
					processFile.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.run();
			}});
	}
	
	private void refresh(){
		try {
			deserializing(processFile);
			this.isFresh=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getString(String key){
		if(!this.isFresh){
			refresh();
		}
		return (String) this.cache.get(key);
	}
	
	public boolean getBoolean(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Boolean) this.cache.get(key);
	}
	
	public byte getByte(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Byte) this.cache.get(key);
	}
	
	public short getShort(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Short) this.cache.get(key);
	}
	
	public char getChar(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Character) this.cache.get(key);
	}
	
	public int getInt(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Integer) this.cache.get(key);
	}
	
	public long getLong(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Long) this.cache.get(key);
	}
	
	public float getFloat(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Float) this.cache.get(key);
	}
	
	public double getDouble(String key){
		if(!this.isFresh){
			refresh();
		}
		return (Double) this.cache.get(key);
	}
	
	public void putString(String key,String value){
		this.cache.put(key, value);
	}
	
	public void putByte(String key,byte value){
		this.cache.put(key, value);
	}
	
	public void putShort(String key,short value){
		this.cache.put(key, value);
	}
	
	public void putChar(String key,char value){
		this.cache.put(key, value);
	}
	
	public void putInt(String key,int value){
		this.cache.put(key, value);
	}
	
	public void putLong(String key,long value){
		this.cache.put(key, value);
	}
	
	public void putFloat(String key,float value){
		this.cache.put(key, value);
	}
	
	public void putDouble(String key,double value){
		this.cache.put(key, value);
	}
	
	public void putBoolean(String key,boolean value){
		this.cache.put(key, value);
	}
	
	private void serializing(File file) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(cache);
		oos.flush();
		oos.close();
	}
	
	
	@SuppressWarnings("unchecked")
	private void deserializing(File file)throws Exception{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream( file));
		this.cache = (HashMap<String,Object>)ois.readObject();
		ois.close();
	}
	
}
