package com.alimuya.ying.sharedr;

import java.util.Map;



/**
 * @author ov_alimuya
 *
 */
public interface ISharedrService {
	
	public void regSelfProcess(String name);
	
	public int count();
	
	public Map<String,ProcessStub> getAllProcess();
	
	
	
}
