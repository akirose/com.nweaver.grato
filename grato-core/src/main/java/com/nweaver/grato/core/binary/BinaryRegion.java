package com.nweaver.grato.core.binary;

public interface BinaryRegion {
	public abstract byte[] getBytes(int l);

	public abstract int getPosition();

	public abstract void update(int l);

	public abstract int getRemainingBytes();

	public abstract int getWrittenBytes();
}
