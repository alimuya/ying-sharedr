package com.alimuya.ying.sharedr;

import com.alimuya.ying.sharedr.exception.SharedrException;

/**
 * @author ov_alimuya
 *
 */
public interface IProcessManager {
	
	public boolean isRunning(int pid);
	
	
	public int getSelfPID() throws SharedrException;
	
	
	
}
