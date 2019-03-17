package com.ardikars.common.memory;

final class NativeMemoryAllocator implements MemoryAllocator {

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
        long address = InternalUnsafeOperation._allocate(capacity);
        return new NativeMemory(address, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
