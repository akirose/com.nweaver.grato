package com.nweaver.grato.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nweaver.grato.Server;
import com.nweaver.grato.ServerContext;
import com.nweaver.grato.ServerException;
import com.nweaver.grato.listener.Listener;

public class DefaultServer implements Server {
	
	private final Logger LOG = LoggerFactory.getLogger(DefaultServer.class);
	
	private ServerContext serverContext;
	
	private boolean suspended = false;
	private boolean started = false;
	

	public DefaultServer(final ServerContext context) {
		this.serverContext = context;
	}

	public void start() throws ServerException {
		if (serverContext == null) {
			// we have already been stopped, can not be restarted
			throw new IllegalStateException("Server has been stopped. Restart is not supported");
		}
		
		List<Listener> startedListeners = new ArrayList<Listener>();
		try {
			Map<String, Listener> listeners = serverContext.getListeners();
			for(Listener listener : listeners.values()) {
				listener.start(serverContext);
				startedListeners.add(listener);
			}
			
			started = true;
			
			LOG.info("Server started");
		} catch (Exception e) {
			// must close listeners that we were able to start
			for(Listener listener : startedListeners) {
				listener.stop();
			}

			if(e instanceof ServerException) {
				throw (ServerException)e;
			} else {
				throw (RuntimeException)e;
			}
		}
	}

	public void stop() {
		if (serverContext == null) {
			return;
		}
		
		// stop all listeners
		Map<String, Listener> listeners = serverContext.getListeners();
		for(Listener listener : listeners.values()) {
			listener.stop();
		}
		
		// release server resources
		if(serverContext != null) {
			serverContext.dispose();
			serverContext = null;
		}
		
		started = false;
	}

	public boolean isStopped() {
		return !started;
	}

	public void suspend() {
		if(!started) {
			return;
		}
		
		LOG.debug("Suspending server");
		
		Map<String, Listener> listeners = serverContext.getListeners();
		for(Listener listener : listeners.values()) {
			listener.suspend();
		}
		
		suspended = true;
		LOG.debug("Server suspended");
	}

	public void resume() {
        if (!suspended) {
            return;
        }

        LOG.debug("Resuming server");
        Map<String, Listener> listeners = serverContext.getListeners();
        for (Listener listener : listeners.values()) {
            listener.resume();
        }

        suspended = false;
        LOG.debug("Server resumed");
	}

	public boolean isSuspended() {
		return suspended;
	}

}
