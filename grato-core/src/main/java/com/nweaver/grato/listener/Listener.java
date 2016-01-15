package com.nweaver.grato.listener;

import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.nweaver.grato.ServerContext;

public interface Listener {

    /**
     * Start the listener, will initiate the listener waiting on the socket. The
     * method should not return until the listener has started accepting socket
     * requests.
     * @param serverContext The current {@link ServerContext}
     * 
     * @throws Exception
     *             On error during start up
     */
	void start(ServerContext serverContext);
	
    /**
     * Stop the listener, it should no longer except socket requests. The method
     * should not return until the listener has stopped accepting socket
     * requests.
     */
    void stop();

    /**
     * Checks if the listener is currently started.
     * 
     * @return False if the listener is started
     */
    boolean isStopped();

    /**
     * Temporarily stops the listener from accepting socket requests. Resume the
     * listener by using the {@link #resume()} method. The method should not
     * return until the listener has stopped accepting socket requests.
     */
    void suspend();

    /**
     * Resumes a suspended listener. The method should not return until the
     * listener has started accepting socket requests.
     */
    void resume();

    /**
     * Checks if the listener is currently suspended
     * 
     * @return True if the listener is suspended
     */
    boolean isSuspended();
    
    /**
     * Returns the currently active sessions for this listener. If no sessions
     * are active, an empty {@link Set} would be returned.
     * 
     * @return The currently active sessions
     */
    Set<? extends IoSession> getActiveSessions();
    
    /**
     * Get the port on which this listener is waiting for requests. For
     * listeners where the port is automatically assigned, this will return the
     * bound port.
     * 
     * @return The port
     */
    int getPort();
    
    /**
     * Get the {@link InetAddress} used for binding the local socket. Defaults
     * to null, that is, the server binds to all available network interfaces
     * 
     * @return The local socket {@link InetAddress}, if set
     */
    String getServerAddress();
    
    /**
     * Get the number of seconds during which no network activity 
     * is allowed before a session is closed due to inactivity.  
     * @return The idle time out
     */
    int getIdleTimeout();
}
