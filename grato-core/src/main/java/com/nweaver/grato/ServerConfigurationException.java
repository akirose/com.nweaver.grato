package com.nweaver.grato;

/**
 * Exception used during startup to indicate that the configuration is not
 * correct.
 * 
 * @author Min-ki,Cho
 */
public class ServerConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 743306955787612323L;
	

	/**
	 * Default constructor.
	 */
	public ServerConfigurationException() {
		super();
	}

    /**
     * Constructs a <code>ServerConfigurationException</code> object with a message.
     * 
     * @param msg
     *            a description of the exception
     */
	public ServerConfigurationException(String msg) {
		super(msg);
	}
	
    /**
     * Constructs a <code>ServerConfigurationException</code> object with a
     * <code>Throwable</code> cause.
     * 
     * @param th
     *            the original cause
     */
	public ServerConfigurationException(Throwable th) {
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
	public ServerConfigurationException(String msg, Throwable th) {
		super(msg, th);
	}
}