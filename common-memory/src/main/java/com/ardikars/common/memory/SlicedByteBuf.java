package com.ardikars.common.memory;

import java.nio.ByteBuffer;

public class SlicedByteBuf extends ByteBuf {

    SlicedByteBuf(int capacity, int maxCapacity) {
        this(capacity, maxCapacity, 0, 0);
    }

    SlicedByteBuf(int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        this(0, null, capacity, maxCapacity, readerIndex, writerIndex);
    }

    SlicedByteBuf(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

    @Override
    public long memoryAddress() {
        long address = super.memoryAddress();
        return address != 0 ? address + baseIndex : address;
    }

}
