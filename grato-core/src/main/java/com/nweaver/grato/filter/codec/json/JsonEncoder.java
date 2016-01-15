package com.nweaver.grato.filter.codec.json;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class JsonEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(message instanceof String) {
			byte[] b = message.toString().getBytes();
			IoBuffer buf = IoBuffer.allocate(b.length + 2);
			buf.put((byte) 0x02);
			buf.putString(message.toString(), Charset.forName("UTF-8").newEncoder());
			buf.put((byte) 0x03);
			buf.flip();
			
			out.write(buf);
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {		
	}

}
