package com.nweaver.grato.filter.codec.json;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class JsonCodecFactory implements ProtocolCodecFactory {
	
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	
	public JsonCodecFactory() {
		encoder = new JsonEncoder();
		decoder = new JsonDecoder();
	}
	
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
