package com.alimuya.ying.sharedr;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ov_alimuya
 * 
 */
public class Java7FileWatcher implements IFileWatcher {
	private WatchService watchService;
	private List<IFileWatcherListner> listeners=new ArrayList<IFileWatcherListner>();
	private Thread watchThread = new Thread() {

		@Override
		public void run() {
			try {
				while (!this.isInterrupted()) {
					final WatchKey key = watchService.take();
					for (WatchEvent<?> watchEvent : key.pollEvents()) {
						final Kind<?> kind = watchEvent.kind();
						if (kind == StandardWatchEventKinds.OVERFLOW) {
							continue;
						}
						
						String path = (String)watchEvent.context();
						Event event = null;
						if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
							event=Event.create;
						}
						if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
							event=Event.write;
						}
						if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
							event=Event.delete;
						}
						if(event!=null){
							int length=listeners.size();
							for (int i = 0; i < length; i++) {
								listeners.get(i).onChange(path, event);
							}
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

	public Java7FileWatcher(File dir) throws IOException {
		Path path = dir.toPath();
		this.watchService = FileSystems.getDefault().newWatchService();
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);
	}

	@Override
	public void start() {
		watchThread.start();
	}

	@Override
	public void close() {
		watchThread.interrupt();
	}

	@Override
	public void regFileWatcherListner(IFileWatcherListner listner) {
		if(listner!=null){
			this.listeners.add(listner);
		}
	}

}
