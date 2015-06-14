package com.alimuya.ying.sharedr;

import com.alimuya.ying.sharedr.IFileWatcher.IFileWatcherListner;

public interface IFileWatcher {
	
	public enum Event{
		write,create,delete
	}
	public static interface IFileWatcherListner {
		public void onChange(String path,Event event);
	}
	public void start();
	
	public void close();

	public void regFileWatcherListner(IFileWatcherListner listner);
}
