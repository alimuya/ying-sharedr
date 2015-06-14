package com.alimuya.ying.sharedr;

import java.io.File;
import java.util.Map;

public class SharedrService implements ISharedrService{
	private static File dir;
	
	public static void initDir(File dir){
		synchronized(dir){
			if(dir==null || !dir.exists()){
				throw new IllegalArgumentException("dir==null || !dir.exists()");
			}
			if(dir!=null){
				throw new RuntimeException("can't repeat the initialization");
			}
			SharedrService.dir=dir;
		}
	}
	
	public SharedrService(){
		if(dir==null){
			throw new RuntimeException("must first initialize dir");
		}
		File[] files = dir.listFiles();
		
	}
	
	@Override
	public void regSelfProcess(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, ProcessStub> getAllProcess() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
