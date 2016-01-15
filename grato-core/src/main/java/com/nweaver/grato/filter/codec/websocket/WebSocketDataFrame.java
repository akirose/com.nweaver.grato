package com.nweaver.grato.filter.codec.websocket;

import org.apache.mina.core.buffer.IoBuffer;

public class WebSocketDataFrame {
	private IoBuffer packet;
	
	public static WebSocketDataFrame build(IoBuffer buf) {
		return new WebSocketDataFrame(buf) ;
	}
	
	protected WebSocketDataFrame(IoBuffer buffer) {
		packet = buffer;
	}
	
	public IoBuffer getPacket() {
		return packet;
	}
}
