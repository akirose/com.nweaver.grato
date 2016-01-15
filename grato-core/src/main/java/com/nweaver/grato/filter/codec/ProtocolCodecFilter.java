package com.nweaver.grato.filter.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.nweaver.grato.core.binary.BinaryRegion;

public class ProtocolCodecFilter extends org.apache.mina.filter.codec.ProtocolCodecFilter {

	public ProtocolCodecFilter(ProtocolCodecFactory factory) {
		super(factory);
	}
	
	public ProtocolCodecFilter(Class<? extends ProtocolEncoder> encoderClass, Class<? extends ProtocolDecoder> decoderClass) {
		super(encoderClass, decoderClass);
	}

	@Override
	public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
		Object message = writeRequest.getMessage();
		if(message instanceof BinaryRegion) {
			nextFilter.filterWrite(session, writeRequest);
			return;
		}
		super.filterWrite(nextFilter, session, writeRequest);
	}
}
