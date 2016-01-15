package com.nweaver.grato;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import com.nweaver.grato.config.ServerConfig;
import com.nweaver.grato.listener.Listener;

public interface ServerContext {
	
	Object getAttribute(Object key);
	
	Object setAttribute(Object key, Object value);
	
	Object removeAttribute(Object key);
	
	boolean containsAttribute(Object key);
	
	Set<Object> getAttributeKeys();

	ServerConfig getServerConfig();
		
	Listener getListener(String name);
	
	Map<String, Listener> getListeners();
	
	void dispose();
	
	ThreadPoolExecutor getThreadPoolExecutor();
	
}
