package com.alimuya.ying.sharedr;

import java.util.Map;


/**
 * @author ov_alimuya
 *
 */
public interface ISharedRAM {
	public void clear();
	public Map<String,?> getAll();
	public  Object get(String key,Object defaultValue);
	public  boolean getBoolean(String key,boolean defaultValue);
	public  byte getByte(String key,byte defaultValue);
	public  short getShort(String key,short defaultValue);
	public  char getChar(String key,char defaultValue);
	public  int getInt(String key,int defaultValue);
	public  long getLong(String key,long defaultValue);
	public  float getFloat(String key,float defaultValue);
	public  double getDouble(String key,double defaultValue);
	
	public boolean setObject(String key,Object value);
	public boolean setByte(String key,byte value);
	public boolean setShort(String key,short value);
	public boolean setChar(String key,char value);
	public boolean setInt(String key,int value);
	public boolean setLong(String key,long value);
	public boolean setFloat(String key,float value);
	public boolean setDouble(String key,double value);
	public boolean setBoolean(String key,boolean value);
	
}
