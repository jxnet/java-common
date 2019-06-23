package com.ardikars.common.memory;

import java.nio.ByteBuffer;

class PooledSlicedByteBuf extends PooledByteBuf {

    PooledSlicedByteBuf(int capacity, int maxCapacity) {
        super(capacity, maxCapacity);
    }

    PooledSlicedByteBuf(int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(capacity, maxCapacity, readerIndex, writerIndex);
    }

    PooledSlicedByteBuf(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
