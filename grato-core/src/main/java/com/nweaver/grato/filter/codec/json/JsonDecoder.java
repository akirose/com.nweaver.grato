package com.nweaver.grato.filter.codec.json;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class JsonDecoder implements ProtocolDecoder {
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "json-context");
	
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		Context context = getContext(session);
		IoBuffer buf = context.getBuffer();
		
		int prevPos = in.position();
		int limit = in.limit();
		
		while(in.hasRemaining()) {
			byte b = in.get();
			boolean matched = false;
			
			switch (b) {
			case 0x02: // STX
				if(buf.position() > 0) {
					buf.clear();					
				}
				break;
			case 0x03: // ETX
				matched = true;
				break;
			}
			
			if (matched) {
				int pos = in.position();
				in.limit(pos - 1);
				in.position(prevPos+1);
				
				context.append(in);
				
				in.limit(limit);
				in.position(pos);
				
				try {
					buf.flip();
					
					out.write(buf.getString(Charset.forName("UTF-8").newDecoder()));
				} finally {
					buf.clear();
				}
			}
		}
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// do nothing
	}

	public void dispose(IoSession session) throws Exception {
		Context ctx = (Context) session.getAttribute(CONTEXT);
		if(ctx != null) {
			session.removeAttribute(CONTEXT);
		}
	}
	
	
    /**
     * Return the context for this session
     */
    private Context getContext(IoSession session) {
        Context ctx;
        ctx = (Context) session.getAttribute(CONTEXT);

        if (ctx == null) {
            ctx = new Context(128);
            session.setAttribute(CONTEXT, ctx);
        }

        return ctx;
    }
	
	private class Context {
		private final IoBuffer buf;
		
		public Context(int bufferLength) {
			buf = IoBuffer.allocate(bufferLength).setAutoExpand(true);
		}
		
		public IoBuffer getBuffer() {
			return buf;
		}
		
		public void append(IoBuffer in) {
			getBuffer().put(in);
		}
	}
}
