package com.nweaver.grato.config;

public class ServerConfig {

	private int maxThreads = 16;
	
	public ServerConfig(int maxThreads) {
		this.maxThreads = maxThreads;
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
	
}
