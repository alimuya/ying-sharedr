package com.alimuya.ying.sharedr;

import java.io.IOException;

public interface IFileWrapper {
	
	public byte[] read() throws IOException;


	void write(byte[] bytes, ISwapConflictHandler handler) throws IOException;
}
