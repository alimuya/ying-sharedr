package com.alimuya.ying.sharedr;

public interface IFileWatcher {
	
	public static interface IFileWatcherListner {
		enum Event{
			write,create,delete
		}
		public void onChange(String path,Event event);
	}
	public void start();
	
	public void close();
	
	public void regFileWatcherListner(IFileWatcherListner listner);
}
