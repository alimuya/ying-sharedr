package com.alimuya.ying.sharedr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alimuya.ying.sharedr.IFileWatcher.Event;
import com.alimuya.ying.sharedr.IFileWatcher.IFileWatcherListner;


/**
 * @author ov_alimuya
 *
 */
public class ProcessStub {
	private String name;
	private volatile boolean isFresh=false;
	private String fileName;
	private int pid;
	private final boolean isSelf;
	private IProcessManager processManager;
	private HashMap<String,Object> cache=new HashMap<String,Object>();
	private IFileWatcherListner listner=new IFileWatcherListner() {

		@Override
		public void onChange(String path, Event event) {
			if(!ProcessStub.this.isSelf && path.equals(fileName)){
				ProcessStub.this.isFresh=false;
			}
		}
	};
	
	public  ProcessStub (String name){
		isSelf=true;
		if(isSelf){
			isFresh=true;
		}else{
			isFresh=false;
		}
		this.name=name;
	}

	public boolean isValid(){
		return false;
	}
	
	public void sync() throws IOException{
		serializing(new File(fileName));
	}
	
	
	private void lockSelfFile() throws IOException{
		final File file=new File(fileName);
		final RandomAccessFile raf=new RandomAccessFile(fileName, "rw");
		final FileLock lock = raf.getChannel().lock();
		Runtime.getRuntime().addShutdownHook(new Thread(){

			@Override
			public void run() {
				try {
					lock.release();
					raf.close();
					file.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.run();
			}});
	}
	
	private void refresh(){
		try {
			deserializing(new File(fileName));
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
