package com.ardikars.common.memory;

import java.nio.ByteBuffer;

class SlicedDirectMemory extends DirectMemory {

    public SlicedDirectMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
