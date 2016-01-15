package com.nweaver.c2p;

public class C2PoolException extends Exception {

	private static final long serialVersionUID = -3642996455221616641L;

	public C2PoolException() {
		super();
	}
	
	public C2PoolException(String msg) {
		super(msg);
	}
	
	public C2PoolException(Throwable cause) {
		super(cause.getMessage());
	}
	
	public C2PoolException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
