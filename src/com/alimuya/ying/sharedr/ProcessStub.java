package com.alimuya.ying.sharedr;

import java.util.HashMap;
import java.util.Map;

import com.alimuya.ying.sharedr.IFileWatcher.IFileWatcherListner;


/**
 * @author ov_alimuya
 *
 */
public class ProcessStub {
	private String name;
	private boolean isFresh=false;
	private int pid;
	private IProcessManager processManager;
	private Map<String,Object> cache=new HashMap<String,Object>();
	private IFileWatcherListner listner=new IFileWatcherListner() {
		
		@Override
		public void onChange(String path, Event event) {
			// TODO Auto-generated method stub
			
		}
	};
	public  ProcessStub (String name){
		this.name=name;
	}

	public boolean isValid(){
		return false;
	}
	
	
	
	public void commit(){
		
	}
	
	
	public Object get(String key){
		return null;
	}
	
	
	public void put(String key,Object value){
		
	}
	
	
	
}
