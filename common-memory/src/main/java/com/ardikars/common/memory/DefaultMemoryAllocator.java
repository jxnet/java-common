package com.ardikars.common.memory;

public class DefaultMemoryAllocator implements MemoryAllocator {

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
        long address = InternalUnsafe._allocate(capacity);
        return new ReadWriteMemory(address, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
