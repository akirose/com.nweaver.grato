package com.nweaver.grato.core.session;

import java.net.SocketAddress;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.core.write.WriteRequestQueue;

import com.nweaver.grato.listener.Listener;

public class IoSessionWrapper implements IoSession {
	
	public static final String ATTRIBUTE_PREFIX = "com.nweaver.grato.";
	public static final String ATTRIBUTE_CACHED_REMOTE_ADDRESS = ATTRIBUTE_PREFIX + "cached-remote-address";
	public static final String ATTRIBUTE_LANGUAGE = ATTRIBUTE_PREFIX + "language";
	public static final String ATTRIBUTE_LAST_ACCESS_TIME = ATTRIBUTE_PREFIX + "last-access-time";
	public static final String ATTRIBUTE_LISTENER = ATTRIBUTE_PREFIX + "listener";
	public static final String ATTRIBUTE_SESSION_ID = ATTRIBUTE_PREFIX + "session-id";
	
	private IoSession wrappedSession;
	
	public IoSessionWrapper(IoSession session) {
		this.wrappedSession = session;
	}
	
	@Deprecated
	public CloseFuture close() {
		return wrappedSession.close();
	}

	public CloseFuture close(boolean immediately) {
		return wrappedSession.close(immediately);
	}

	public boolean containsAttribute(Object key) {
		return wrappedSession.containsAttribute(key);
	}

	@Deprecated
	public Object getAttachment() {
		return wrappedSession.getAttachment();
	}

	public Object getAttribute(Object key) {
		return wrappedSession.getAttribute(key);
	}

	public Object getAttribute(Object key, Object defaultValue) {
		return wrappedSession.getAttribute(key, defaultValue);
	}
	
	public Set<Object> getAttributeKeys() {
		return wrappedSession.getAttributeKeys();
	}

	public int getBothIdleCount() {
		return wrappedSession.getBothIdleCount();
	}

	public CloseFuture getCloseFuture() {
		return wrappedSession.getCloseFuture();
	}

	public IoSessionConfig getConfig() {
		return wrappedSession.getConfig();
	}

	public long getCreationTime() {
		return wrappedSession.getCreationTime();
	}

	public IoFilterChain getFilterChain() {
		return wrappedSession.getFilterChain();
	}

	public IoHandler getHandler() {
		return wrappedSession.getHandler();
	}

	public long getId() {
		return wrappedSession.getId();
	}

	public int getIdleCount(IdleStatus status) {
		return wrappedSession.getIdleCount(status);
	}

	public long getLastBothIdleTime() {
		return wrappedSession.getLastBothIdleTime();
	}

	public long getLastIdleTime(IdleStatus status) {
		return wrappedSession.getLastIdleTime(status);
	}

	public long getLastIoTime() {
		return wrappedSession.getLastIoTime();
	}

	public long getLastReadTime() {
		return wrappedSession.getLastReadTime();
	}

	public long getLastReaderIdleTime() {
		return wrappedSession.getLastReaderIdleTime();
	}

	public long getLastWriteTime() {
		return wrappedSession.getLastWriteTime();
	}

	public long getLastWriterIdleTime() {
		return wrappedSession.getLastWriterIdleTime();
	}

	public SocketAddress getLocalAddress() {
		return wrappedSession.getLocalAddress();
	}

	public long getReadBytes() {
		return wrappedSession.getReadBytes();
	}

	public double getReadBytesThroughput() {
		return wrappedSession.getReadBytesThroughput();
	}

	public long getReadMessages() {
		return wrappedSession.getReadMessages();
	}

	public double getReadMessagesThroughput() {
		return wrappedSession.getReadMessagesThroughput();
	}

	public int getReaderIdleCount() {
		return wrappedSession.getReaderIdleCount();
	}
	
	public Object getCurrentWriteMessage() {
		return wrappedSession.getCurrentWriteMessage();
	}

	public WriteRequest getCurrentWriteRequest() {
		return wrappedSession.getCurrentWriteRequest();
	}
	
	public boolean isBothIdle() {
		return wrappedSession.isBothIdle();
	}

	public boolean isReaderIdle() {
		return wrappedSession.isReaderIdle();
	}

	public boolean isWriterIdle() {
		return wrappedSession.isWriterIdle();
	}
	
	public WriteRequestQueue getWriteRequestQueue() {
		return wrappedSession.getWriteRequestQueue();
	}
	
	public boolean isReadSuspended() {
		return wrappedSession.isReadSuspended();
	}

	public boolean isWriteSuspended() {
		return wrappedSession.isWriteSuspended();
	}

	public void setCurrentWriteRequest(WriteRequest currentWriteRequest) {
		wrappedSession.setCurrentWriteRequest(currentWriteRequest);
	}

	public void updateThroughput(long currentTime, boolean force) {
		wrappedSession.updateThroughput(currentTime, force);
	}

	public long getScheduledWriteBytes() {
		return wrappedSession.getScheduledWriteBytes();
	}

	public int getScheduledWriteMessages() {
		return wrappedSession.getScheduledWriteMessages();
	}

	public IoService getService() {
		return wrappedSession.getService();
	}

	public SocketAddress getServiceAddress() {
		return wrappedSession.getServiceAddress();
	}

	public TransportMetadata getTransportMetadata() {
		return wrappedSession.getTransportMetadata();
	}

	public int getWriterIdleCount() {
		return wrappedSession.getWriterIdleCount();
	}

	public long getWrittenBytes() {
		return wrappedSession.getWrittenBytes();
	}

	public double getWrittenBytesThroughput() {
		return wrappedSession.getWrittenBytesThroughput();
	}

	public long getWrittenMessages() {
		return wrappedSession.getWrittenMessages();
	}

	public double getWrittenMessagesThroughput() {
		return wrappedSession.getWrittenMessagesThroughput();
	}

	public boolean isClosing() {
		return wrappedSession.isClosing();
	}

	public boolean isConnected() {
		return wrappedSession.isConnected();
	}

	public boolean isIdle(IdleStatus status) {
		return wrappedSession.isIdle(status);
	}

	public ReadFuture read() {
		return wrappedSession.read();
	}

	public Object removeAttribute(Object key) {
		return wrappedSession.removeAttribute(key);
	}

	public boolean removeAttribute(Object key, Object value) {
		return wrappedSession.removeAttribute(key, value);
	}

	public boolean replaceAttribute(Object key, Object oldValue, Object newValue) {
		return wrappedSession.replaceAttribute(key, oldValue, newValue);
	}

	public void resumeRead() {
		wrappedSession.resumeRead();
	}

	public void resumeWrite() {
		wrappedSession.resumeWrite();
	}

	@SuppressWarnings("deprecation")
	public Object setAttachment(Object attachment) {
		return wrappedSession.setAttachment(attachment);
	}

	public Object setAttribute(Object key) {
		return wrappedSession.setAttribute(key);
	}

	public Object setAttribute(Object key, Object value) {
		return wrappedSession.setAttribute(key, value);
	}

	public Object setAttributeIfAbsent(Object key) {
		return wrappedSession.setAttributeIfAbsent(key);
	}

	public Object setAttributeIfAbsent(Object key, Object value) {
		return wrappedSession.setAttributeIfAbsent(key, value);
	}

	public void suspendRead() {
		wrappedSession.suspendRead();
	}

	public void suspendWrite() {
		wrappedSession.suspendWrite();
	}

	public WriteFuture write(Object message) {
		WriteFuture future = wrappedSession.write(message);
		return future;
	}

	public WriteFuture write(Object message, SocketAddress destination) {
		WriteFuture future = wrappedSession.write(message, destination);
		return future;
	}
	
	public SocketAddress getRemoteAddress() {
		// when closing a socket, the remote address might be reset to null
		// therefore, we attempt to keep a cached copy around
		SocketAddress address = wrappedSession.getRemoteAddress();
		if(address == null && containsAttribute(ATTRIBUTE_CACHED_REMOTE_ADDRESS)) {
			return (SocketAddress) getAttribute(ATTRIBUTE_CACHED_REMOTE_ADDRESS);
		} else {
			setAttribute(ATTRIBUTE_CACHED_REMOTE_ADDRESS, address);
			return address;
		}
	} // End of IoSession methods
	
	public String getLanguage() {
		return (String) getAttribute(ATTRIBUTE_LANGUAGE);
	}
	
	public void setLanguage(String language) {
		setAttribute(ATTRIBUTE_LANGUAGE, language);
	}
	
	public Date getLastAccessTime() {
		return (Date) getAttribute(ATTRIBUTE_LAST_ACCESS_TIME);
	}
	
	public void updateLastAccessTime() {
		setAttribute(ATTRIBUTE_LAST_ACCESS_TIME, new Date());
	}
	
	public Listener getListener() {
		return (Listener) getAttribute(ATTRIBUTE_LISTENER);
	}

	public void setListener(Listener listener) {
		setAttribute(ATTRIBUTE_LISTENER, listener);
	}
	
	public UUID getSessionId() {
		synchronized (wrappedSession) {
			if(!wrappedSession.containsAttribute(ATTRIBUTE_SESSION_ID)) {
				wrappedSession.setAttribute(ATTRIBUTE_SESSION_ID, UUID.randomUUID());
			}
			return (UUID) wrappedSession.getAttribute(ATTRIBUTE_SESSION_ID);
		}
	}
}
