package com.nweaver.grato.filter.codec.websocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class WebSocketDecoder extends CumulativeProtocolDecoder {
	
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		IoBuffer resultBuffer;
		
		if(!session.containsAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED)) {
			if(tryWebSocketHandShake(session, in, out)) {
				in.position(in.limit());
				session.setAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED, true);
				return true;
			} else {
				resultBuffer = IoBuffer.wrap(in.array(), 0, in.limit());
				in.position(in.limit());
				session.setAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED, false);
			}
		} else if(session.containsAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED) && ((Boolean)session.getAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED))) {
			int pos = in.position();
			resultBuffer = WebSocketDecoder.buildWSDataBuffer(in, session);
			if(resultBuffer == null) {
				in.position(pos);
				return false;
			}
		} else {
			resultBuffer = IoBuffer.wrap(in.array(), 0, in.limit());
			in.position(in.limit());
		}
		
		out.write(resultBuffer);
		return true;
	}
	
	private boolean tryWebSocketHandShake(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
		try {
			String requestbody = new String(in.array());
			String[] headerFields = requestbody.split("\r\n");

			String origin = "";
			String socketKey = "";
			String socketVersion = "";
			
			if(!headerFields[0].matches("^(GET|POST) .+ HTTP\\/1\\.[0-1]")) {
				return false;
			}
			
			for(int i = 1; i < headerFields.length; i++) {
				int index = headerFields[i].indexOf(':');
				if(index < 0) {
					continue;
				}
				
				if(headerFields[i].contains("Sec-WebSocket-Key")) {
					socketKey = headerFields[i].substring(index + 1).trim();
				} else if(headerFields[i].contains("Origin")) {
					origin = headerFields[i].substring(index + 1).trim();
				} else if(headerFields[i].contains("Sec-WebSocket-Version")) {
					socketVersion = headerFields[i].substring(index + 1).trim();
				}
			}
			
			// TODO : validate allowed Origin 
			if(origin.isEmpty()) {
				return false;
			}
			// TODO : check client version is supported
			if(socketVersion.isEmpty()) {
				return false;
			}
			if(socketKey.isEmpty()) {
				return false;
			}
			
			String acceptKey = WebSocketUtils.getWebSocketResponseKey(socketKey);
			String response = "HTTP/1.1 101 Switching Protocols\r\n";
			response += "Upgrade: websocket\r\n";
			response += "Connection: Upgrade\r\n";
			response += "Sec-WebSocket-Accept: " + acceptKey + "\r\n";
			response += "\r\n";
			session.write(response);

			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
    private static IoBuffer buildWSDataBuffer(IoBuffer in, IoSession session) {

        IoBuffer resultBuffer = null;
        do{
            byte frameInfo = in.get();            
            byte opCode = (byte) (frameInfo & 0x0F);
            if (opCode == 8) {
                session.close(true);
                return resultBuffer;
            }        
            int frameLen = (in.get() & (byte) 0x7F);
            if(frameLen == 126){
                frameLen = in.getShort();
            }
            
            if(frameLen+4 > in.remaining()){                
                return null;
            }
            byte mask[] = new byte[4];
            for (int i = 0; i < 4; i++) {
                mask[i] = in.get();
            }
             
            byte[] unMaskedPayLoad = new byte[frameLen];
            for (int i = 0; i < frameLen; i++) {
                byte maskedByte = in.get();
                unMaskedPayLoad[i] = (byte) (maskedByte ^ mask[i % 4]);
            }
            
            if(resultBuffer == null){
                resultBuffer = IoBuffer.wrap(unMaskedPayLoad);
                resultBuffer.position(resultBuffer.limit());
                resultBuffer.setAutoExpand(true);
            }
            else{
                resultBuffer.put(unMaskedPayLoad);
            }
        }
        while(in.hasRemaining());
        
        resultBuffer.flip();
        return resultBuffer;

    }
}
