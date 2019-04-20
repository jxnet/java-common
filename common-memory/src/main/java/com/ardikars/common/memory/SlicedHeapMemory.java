package com.ardikars.common.memory;

import java.nio.ByteBuffer;

class SlicedHeapMemory extends HeapMemory {

    public SlicedHeapMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
