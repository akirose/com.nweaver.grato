package com.nweaver.grato.core.binary;

import java.util.Arrays;

public class DefaultBinaryRegion implements BinaryRegion {
	private byte[] binary;

	private int position = 0;
	private int remainingBytes;
	
	public DefaultBinaryRegion(byte[] byteArray) {
		if(byteArray == null)
			throw new IllegalArgumentException("byte array can not be null");
		
		binary = byteArray;
		remainingBytes = byteArray.length;
	}
	
	public int getWrittenBytes() {
		return position;
	}
	
	public int getRemainingBytes() {
		return remainingBytes;
	}
	
	public int getPosition() {
		return position;
	}
	
	public byte[] getBytes(int value) {
		return Arrays.copyOfRange(binary, position, position+value);
	}
	
	public void update(int value) {
		position += value;
		remainingBytes -= value;
	}
}
