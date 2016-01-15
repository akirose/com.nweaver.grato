package com.nweaver.grato.core;

import org.apache.mina.core.buffer.IoBuffer;

public interface Request {

	String getCommand();
	
	boolean hasArgument();
	
	IoBuffer getArgument();
	
}
