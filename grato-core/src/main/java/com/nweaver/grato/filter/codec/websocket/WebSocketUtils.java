package com.nweaver.grato.filter.codec.websocket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.util.Base64;

public class WebSocketUtils {
	
	public static final AttributeKey ATTRIBUTE_HANDSHAKED = new AttributeKey(WebSocketUtils.class, "com.nweaver.grato.ws.handshake-state");
	
	static String getWebSocketResponseKey(String socketKey) throws NoSuchAlgorithmException {
		socketKey += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(socketKey.getBytes());
		byte[] hashedValue = digest.digest();
		return new String(Base64.encodeBase64(hashedValue));
	}
}
