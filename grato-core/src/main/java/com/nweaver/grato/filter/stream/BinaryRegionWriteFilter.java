package com.nweaver.grato.filter.stream;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.stream.AbstractStreamWriteFilter;

import com.nweaver.grato.core.binary.BinaryRegion;

public class BinaryRegionWriteFilter extends AbstractStreamWriteFilter<BinaryRegion> {

	protected Class<BinaryRegion> getMessageClass() {
		return BinaryRegion.class;
	}

	protected IoBuffer getNextBuffer(BinaryRegion region) throws IOException {
		if(region.getRemainingBytes() <= 0) {
			return null;
		}
		
		final int bufferSize = (int) Math.min(getWriteBufferSize(), region.getRemainingBytes());
		IoBuffer buffer = IoBuffer.allocate(bufferSize);
		
		byte[] bytes = region.getBytes(bufferSize);
		buffer.put(bytes);
		region.update(bytes.length);
		
		buffer.flip();
		return buffer;
	}

}
