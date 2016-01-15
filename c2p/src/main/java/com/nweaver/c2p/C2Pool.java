package com.nweaver.c2p;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

public class C2Pool {
	protected Config config = new Config();
	protected PoolableConnectionFactory connectionFactory;
	
	protected volatile GenericObjectPool<Connection> pool = null;
	
	/**
	 * Default constructor
	 */
	public C2Pool() {
	}
	
	public C2Pool(PoolableConnectionFactory connectionFactory, Config config) {
		this.connectionFactory = connectionFactory;
		this.config = config;
	}
	
	public synchronized GenericObjectPool<Connection> create() throws C2PoolException {
		if(this.pool != null) {
			return this.pool;
		}
		
		if(config.maxWait > 0) {
			config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
		} else {
			config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_FAIL;
		}
		config.testOnBorrow = true;
		config.testOnReturn = true;
		
		GenericObjectPool<Connection> objectPool = new GenericObjectPool<Connection>(connectionFactory, config);
		
		try {
			for(int i = 0; i < config.minIdle; i++) {
				objectPool.addObject();
			}
		} catch (Exception e) {
			throw new C2PoolException("Connection pool initializing failed.", e.getCause());
		}
		
		this.pool = objectPool;
		
		return this.pool;
	}
	
	public synchronized void dispose() throws C2PoolException {
		GenericObjectPool<Connection> oldpool = this.pool;
		this.pool = null;
		
		try {
			if(oldpool != null) {
				oldpool.close();
			}
		} catch (Exception e) {
			throw new C2PoolException("Connection pool destroy failed.", e.getCause());
		}
	}
	
	public PoolableConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	public void setConnectionFactory(PoolableConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public int getMaxIdle() {
		return config.maxIdle;
	}
	
	public void setMaxIdle(int maxIdle) {
		config.maxIdle = maxIdle;
	}
	
	public int getMinIdle() {
		return config.minIdle;
	}
	
	public void setMinIdle(int minIdle) {
		config.minIdle = minIdle;
	}
	
	public int getMaxActive() {
		return config.maxActive;
	}
	
	public void setMaxActive(int maxActive) {
		config.maxActive = maxActive;
	}
	
	public long getMaxWait() {
		return config.maxWait;
	}
	
	public void setMaxWait(long maxWait) {
		config.maxWait = maxWait;
	}
	
	public boolean getLifo() {
		return config.lifo;
	}
	
	public void setLifo(boolean lifo) {
		config.lifo = lifo;
	}
}
