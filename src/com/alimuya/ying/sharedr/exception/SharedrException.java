package com.alimuya.ying.sharedr.exception;

/**
 * @author ov_alimuya
 *
 */
public class SharedrException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SharedrException (){
		super();
	}
	
	public SharedrException (String message){
		super(message);
	}
	
	public SharedrException  (String message,Throwable e){
		super(message,e);
	}
}
