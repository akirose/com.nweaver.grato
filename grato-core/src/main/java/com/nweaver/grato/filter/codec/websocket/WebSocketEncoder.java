package com.nweaver.grato.filter.codec.websocket;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class WebSocketEncoder extends ProtocolEncoderAdapter {
	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		boolean isHandShakeResponse = (message instanceof String) && !(session.containsAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED));
		boolean isDataFramePacket = ((message instanceof WebSocketDataFrame) || (message instanceof String)) &&
										(session.containsAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED));
		boolean isWebSocketSession = session.containsAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED) &&
										((Boolean) session.getAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED));
		
		IoBuffer resultBuffer;
		if(isHandShakeResponse) {
			String response = (String) message;
			byte[] responseBytes = response.getBytes();
			resultBuffer = IoBuffer.wrap(responseBytes, 0, responseBytes.length);
			
			session.setAttribute(WebSocketUtils.ATTRIBUTE_HANDSHAKED, true);
		}
		else if(isDataFramePacket) {
			if(isWebSocketSession) {
				resultBuffer = WebSocketEncoder.buildWSDataFrameBuffer(message);
			} else {
				if(message instanceof WebSocketDataFrame) {
					resultBuffer = ((WebSocketDataFrame) message).getPacket();
				} else {
					byte[] msgBytes = ((String) message).getBytes(Charset.forName("UTF-8"));
					resultBuffer = IoBuffer.wrap(msgBytes, 0, msgBytes.length);
				}
			}
		}
		else {
			throw new Exception("Message not a WebSocket type");
		}
		
		out.write(resultBuffer);
	}
	
	private static IoBuffer buildWSDataFrameBuffer(Object message) {
		boolean isBinary = (message instanceof WebSocketDataFrame);

		IoBuffer packet;
		if(isBinary) {
			packet = ((WebSocketDataFrame) message).getPacket();
		} else {
			byte[] msgBytes = ((String) message).getBytes(Charset.forName("UTF-8"));
			packet = IoBuffer.wrap(msgBytes, 0, msgBytes.length);
		}
		
		IoBuffer buffer = IoBuffer.allocate(packet.limit() + 2);

		if(isBinary) {
			buffer.put((byte) 0x82);
		} else {
			buffer.put((byte) 0x81);
		}
		
		if(packet.limit() <= 125) {
			buffer.put((byte) packet.limit());
		} else {
			buffer.expand(buffer.capacity()+2);
			buffer.put((byte) 126);
			buffer.putShort((short) packet.limit());
		}
		buffer.put(packet);
		buffer.flip();
		return buffer;
	}

}
