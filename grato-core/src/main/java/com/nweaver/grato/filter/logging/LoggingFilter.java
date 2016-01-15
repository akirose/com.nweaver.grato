package com.nweaver.grato.filter.logging;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFilter extends org.apache.mina.filter.logging.LoggingFilter {
	
	private final Logger LOG;
	
	public LoggingFilter() {
		this(LoggingFilter.class.getName());
	}

	public LoggingFilter(Class<?> clazz) {
		this(clazz.getName());
	}
	
	public LoggingFilter(String name) {
		super(name);
		
		LOG = LoggerFactory.getLogger(name);
	}
	
	@Override
	public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
		Object message = writeRequest.getMessage();
		
		if(message instanceof IoBuffer) {
			log(getMessageReceivedLogLevel(), "SENT " + session.getRemoteAddress() + "\n{}", format((IoBuffer) message));
		}
		
		nextFilter.messageSent(session, writeRequest);
	}
	
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		if(message instanceof IoBuffer) {
			log(getMessageReceivedLogLevel(), "RECEIVED\n{}", format((IoBuffer) message));
		}

		nextFilter.messageReceived(session, message);
	}
	
	private String format(IoBuffer in) {
		if(in.limit() <= 0) return "";
		
		StringBuffer buf = new StringBuffer();
		buf.append("00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F     0123456789ABCDEF \n");
		buf.append("===============================================     ================ \n");
		
		int mark = in.position();
		in.rewind();
		
		int size = in.remaining();
		byte[] data = new byte[size];
		for(int i = 0; i < size; i++) {
			data[i] = in.get();
		}
		in.position(mark);
		
		int line = (int) Math.ceil((double)data.length / 16);
		
		for(int i = 0; i < line; i++) {
			int word = ((i+1)*16) < data.length ? ((i+1)*16) : data.length;
			
			for(int j = i*16; j < word; j++) {
				buf.append(String.format("%02x ", 0xff & data[j]).toUpperCase());
			}
			buf.append("    ");
			if((word%16) != 0) {
				for(int j = 0; j < 16-(word%16); j++) {
					buf.append("   ");
				}
			}
			for(int j = i*16; j < word; j++) {
				if(data[j] < 33) {
					buf.append(".");
				} else {
					buf.append((char) data[j]);
				}
			}
			if(i < (line-1)) {
				buf.append("\n");
			}
		}
		
		return buf.toString();
	}
	
	private void log(LogLevel eventLevel, String message, Object param) {
		switch(eventLevel) {
			case TRACE: LOG.trace(message, param); return;
			case DEBUG: LOG.debug(message, param); return;
			case INFO: LOG.info(message, param); return;
			case WARN: LOG.warn(message, param); return;
			case ERROR: LOG.error(message, param); return;
			default: return;
		}
	}
}
