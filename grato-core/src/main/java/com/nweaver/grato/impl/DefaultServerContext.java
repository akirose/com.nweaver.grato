package com.nweaver.grato.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nweaver.grato.ServerContext;
import com.nweaver.grato.config.ServerConfig;
import com.nweaver.grato.config.ServerConfigFactory;
import com.nweaver.grato.listener.Listener;

public class DefaultServerContext implements ServerContext {
	
	private final Logger LOG = LoggerFactory.getLogger(DefaultServerContext.class);
	
	private ServerConfig serverConfig = new ServerConfigFactory().createServerConfig();
	
	private ConcurrentHashMap<Object, Object> attributes = new ConcurrentHashMap<Object, Object>(4);
	
	private Map<String, Listener> listeners = new HashMap<String, Listener>();
	
	
	public DefaultServerContext() {
	}
	
	/**
	 * The thread pool executor to be used by the server using this context
	 */
	private ThreadPoolExecutor threadPoolExecutor = null;

	public ServerConfig getServerConfig() {
		return serverConfig;
	}
	
	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
	
	public void dispose() {
		listeners.clear();
		
		if(threadPoolExecutor != null) {
			LOG.debug("Shutting down the thread pool executor");
			threadPoolExecutor.shutdown();
			try {
				threadPoolExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
			} finally {
			}
		}
	}
	
	public Object getAttribute(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("key");
		}
		
		Object object = attributes.get(key);
		
		return object;
	}
	
	public Object setAttribute(Object key, Object value) {
		if(key == null) {
			throw new IllegalArgumentException("key");
		}
		
		if(value == null) {
			return attributes.remove(key);
		}
		
		return attributes.put(key, value);
	}
	
	public Object removeAttribute(Object key) {
		if(key == null) {
			throw new IllegalArgumentException("key");
		}
		
		return attributes.remove(key);
	}
	
	public boolean containsAttribute(Object key) {
		return attributes.containsKey(key);
	}
	
	public Set<Object> getAttributeKeys() {
		synchronized (attributes) {
			return new HashSet<Object>(attributes.keySet());
		}
	}

	public Listener getListener(String name) {
		return listeners.get(name);
	}

	public Map<String, Listener> getListeners() {
		return listeners;
	}
	
	public void setListeners(Map<String, Listener> listeners) {
		this.listeners = listeners;
	}
	
	public void addListener(String name, Listener listener) {
		listeners.put(name, listener);
	}
	
	public Listener removeListener(String name) {
		return listeners.remove(name);
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		if(threadPoolExecutor == null) {
			int maxThreads = serverConfig.getMaxThreads();
			if(maxThreads < 1) {
				maxThreads = 16;
			}
			LOG.debug("Intializing shared thread pool executor with max threads of {}", maxThreads);
			threadPoolExecutor = new OrderedThreadPoolExecutor(maxThreads);
		}
		return threadPoolExecutor;
	}

}
