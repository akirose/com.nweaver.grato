package com.nweaver.c2p;

import org.apache.commons.pool.PoolableObjectFactory;

public abstract class PoolableConnectionFactory implements PoolableObjectFactory<Connection> {

	public abstract void destroyObject(Connection connection) throws Exception;

	public abstract Connection makeObject();
	
	public abstract boolean validateObject(Connection connection);
	
	public void activateObject(Connection connection) throws Exception {
	}

	public void passivateObject(Connection connection) throws Exception {
	}

}
