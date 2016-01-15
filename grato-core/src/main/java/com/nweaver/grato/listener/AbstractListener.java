package com.nweaver.grato.listener;


public abstract class AbstractListener implements Listener {

	private String serverAddress;
	
	private int port = 0;
	
	private int idleTimeout;
	
	public AbstractListener(String serverAddress, int port, int idleTimeout) {
		this.serverAddress = serverAddress;
		this.port = port;
		this.idleTimeout = idleTimeout;
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public int getPort() {
		return port;
	}
	
    /**
     * Used internally to update the port after binding
     * @param port
     */
	public void setPort(int port) {
		this.port = port;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public String getServerAddress() {
		return serverAddress;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public int getIdleTimeout() {
		// TODO Auto-generated method stub
		return idleTimeout;
	}

}
