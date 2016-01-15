package com.nweaver.grato.listener;

import java.net.InetAddress;

public abstract class AbstractListenerFactory {

    private String serverAddress;

    private int port = 10001;
    
    private int idleTimeout = 300;
    
    /**
     * Default constructor
     */
    public AbstractListenerFactory() {
        // do nothing
    }
    
    /**
     * Copy constructor, will copy properties from the provided listener.
     * @param listener The listener which properties will be used for this factory
     */
    public AbstractListenerFactory(Listener listener) {
    	serverAddress = listener.getServerAddress();
    	port = listener.getPort();
    	idleTimeout = listener.getIdleTimeout();
    }
    
    /**
     * Create a listener based on the settings of this factory. The listener is immutable.
     * @return The created listener
     */
    public abstract Listener createListener();
    
    /**
     * Get the port on which listeners created by this factory is waiting for requests. 
     * 
     * @return The port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port on which listeners created by this factory will accept requests. Or set to 0
     * (zero) is the port should be automatically assigned
     * 
     * @param port
     *            The port to use.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the {@link InetAddress} used for binding the local socket. Defaults
     * to null, that is, the server binds to all available network interfaces
     * 
     * @return The local socket {@link InetAddress}, if set
     */
    public String getServerAddress()  {
        return serverAddress;
    }

    /**
     * Set the {@link InetAddress} used for binding the local socket. Defaults
     * to null, that is, the server binds to all available network interfaces
     * 
     * @param serverAddress
     *            The local socket {@link InetAddress}
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
	
    /**
     * Get the number of seconds during which no network activity 
     * is allowed before a session is closed due to inactivity.  
     * @return The idle time out
     */
    public int getIdleTimeout() {
        return idleTimeout;
    }
    
    /**
     * Set the number of seconds during which no network activity 
     * is allowed before a session is closed due to inactivity.  
     *
     * @param idleTimeout The idle timeout in seconds
     */
    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }
    
    
}
