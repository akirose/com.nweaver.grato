package com.nweaver.grato.core.service;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.nweaver.grato.ServerContext;
import com.nweaver.grato.listener.Listener;

public abstract class AbstractIoHandler implements IoHandler {
	
	public AbstractIoHandler() {
	}
	
	public AbstractIoHandler(ServerContext context, Listener listener) {
		init(context, listener);
	}
	
	public abstract void init(ServerContext context, Listener listener);

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {		
	}

	public void messageReceived(IoSession session, Object message) throws Exception {		
	}

	public void messageSent(IoSession session, Object message) throws Exception {
	}

	public void sessionClosed(IoSession session) throws Exception {
	}

	public void sessionCreated(IoSession session) throws Exception {
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	public void sessionOpened(IoSession session) throws Exception {
	}

}
