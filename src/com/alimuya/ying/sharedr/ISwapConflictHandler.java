package com.alimuya.ying.sharedr;

public interface ISwapConflictHandler {
	public byte[] handlConflict(byte[] newData,byte[] OldData) throws FatalConflictException;
}
