package com.nweaver.grato.config;

/**
 * Factory for creating connection configurations
 * 
 * @author Min-ki,Cho
 */
public class ServerConfigFactory {

	private int maxThreads = 16;
	
    /**
     * Create a connection configuration instances based on the configuration on this factory
     * @return The {@link ServerConfig} instance
     */
	public ServerConfig createServerConfig() {
		return new ServerConfig(maxThreads);
	}
	
    /**
     * Returns the maximum number of threads the server is allowed to create for
     * processing client requests.
     * 
     * @return the maximum number of threads the server is allowed to create for
     *         processing client requests.
     */	
	public int getMaxThreads() {
		return maxThreads;
	}
	
    /**
     * Sets the maximum number of threads the server is allowed to create for
     * processing client requests.
     * 
     * @param maxThreads
     *            the maximum number of threads the server is allowed to create
     *            for processing client requests.
     */
	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}
	
}
