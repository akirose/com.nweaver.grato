package com.nweaver.grato;


/**
 * Server exception class.
 * 
 * @author Min-ki,Cho
 */
public class ServerException extends Exception {

	private static final long serialVersionUID = -909890751161933455L;

	
	/*
	 * Default constructor.
	 */
	public ServerException() {
		super();
	}

    /**
     * Constructs a <code>ServerException</code> object with a message.
     * 
     * @param msg
     *            a description of the exception
     */
	public ServerException(String msg) {
		super(msg);
	}
	
    /**
     * Constructs a <code>ServerException</code> object with a
     * <code>Throwable</code> cause.
     * 
     * @param th
     *            the original cause
     */
	public ServerException(Throwable th) {
		super(th.getMessage());
	}
	
    /**
     * Constructs a <code>BaseException</code> object with a
     * <code>Throwable</code> cause.
     * @param msg A description of the exception
     * 
     * @param th
     *            The original cause
     */
	public ServerException(String msg, Throwable th) {
		super(msg, th);
	}
}