package com.ardikars.common.memory;

import java.nio.ByteBuffer;

final class HeapMemoryAllocator implements MemoryAllocator {

    @Override
    public Memory allocate(int capacity) {
        return allocate(capacity, capacity, 0, 0);
    }

    @Override
    public Memory allocate(int capacity, int maxCapacity) {
        return allocate(capacity, maxCapacity, 0, 0);
    }

    @Override
    public Memory allocate(int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        return new HeapMemory(0, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
