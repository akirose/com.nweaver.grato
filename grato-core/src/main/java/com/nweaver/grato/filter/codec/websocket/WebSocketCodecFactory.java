package com.nweaver.grato.filter.codec.websocket;

import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class WebSocketCodecFactory implements ProtocolCodecFactory {
	public static final AttributeKey ATTRIBUTE_HANDSHAKED = new AttributeKey(WebSocketCodecFactory.class, "isHandshaked");
	
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	
	public WebSocketCodecFactory() {
		encoder = new WebSocketEncoder();
		decoder = new WebSocketDecoder();
	}
	
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}
	
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
}
