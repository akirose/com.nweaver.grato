package com.nweaver.grato.examples.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nweaver.grato.ServerConfigurationException;
import com.nweaver.grato.ServerContext;
import com.nweaver.grato.filter.logging.LoggingFilter;
import com.nweaver.grato.listener.AbstractListener;

public class EchoListener extends AbstractListener {
	
	private final Logger LOG = LoggerFactory.getLogger(EchoListener.class);
	
	private SocketAcceptor acceptor;
	private InetSocketAddress address;
	
	boolean suspended = false;
	
	private EchoHandler handler = new EchoHandler();
	private ServerContext context;
	
	public EchoListener() {
		this(null, 3000, 0);
	}

	public EchoListener(String serverAddress, int port, int idleTimeout) {
		super(serverAddress, port, idleTimeout);
	}

	public synchronized void start(ServerContext context) {
		if(!isStopped()) {
            // listener already started, don't allow
            throw new IllegalStateException("Listener already started");
		}
		
		try {
			this.context = context;
			
			acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors());
			
			if(getServerAddress() != null) {
				address = new InetSocketAddress(getServerAddress(), getPort());
			} else {
				address = new InetSocketAddress(getPort());
			}
			
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setReadBufferSize(2048);
			// Decrease the default receiver buffer size
			((SocketSessionConfig) acceptor.getSessionConfig()).setReceiveBufferSize(512);
			
			acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(context.getThreadPoolExecutor()));
			acceptor.getFilterChain().addLast("logging", new LoggingFilter());
			
			handler.init(context, this);
			acceptor.setHandler(handler);
			
			try {
				acceptor.bind(address);
			} catch (IOException e) {
				throw new ServerConfigurationException("Failed to bind to address " + address);
			}
			
			updatePort();
		} catch (RuntimeException e) {
			// clean up if we fail to start
			stop();
			throw e;
		}
	}
	
	private void updatePort() {
		setPort(acceptor.getLocalAddress().getPort());
	}

	public synchronized void stop() {
		// close server socket
		if(acceptor != null) {
			acceptor.unbind();
			acceptor.dispose();
			acceptor = null;
		}
		context = null;
	}

	public boolean isStopped() {
		return acceptor == null;
	}
	
	public boolean isSuspended() {
		return suspended;
	}

	public synchronized void suspend() {
		if(acceptor != null & !suspended) {
			LOG.debug("Suspending listener");
			acceptor.unbind();
			
			suspended = true;
			LOG.debug("Listener suspended");
		}
	}

	public synchronized void resume() {
		if(acceptor != null && suspended) {
			try {
				LOG.debug("Resuming listener");
				acceptor.bind(address);
				LOG.debug("Listener resumed");
				
				updatePort();
				
				suspended = false;
			} catch (IOException e) {
				LOG.error("Failed to resume listener", e);
			}
		}
	}

	public synchronized Set<IoSession> getActiveSessions() {
		Map<Long, IoSession> sessions = acceptor.getManagedSessions();
		
		Set<IoSession> activeSessions = new HashSet<IoSession>();
		for(IoSession session : sessions.values()) {
			activeSessions.add(session);
		}
		
		return activeSessions;
	}

}
