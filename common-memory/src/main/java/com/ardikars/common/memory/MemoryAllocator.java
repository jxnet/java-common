package com.ardikars.common.memory;

public interface MemoryAllocator {

    Memory allocate(int capacity);

    Memory allocate(int capacity, int maxCapacity);

    Memory allocate(int capacity, int maxCapacity, int readerIndex, int writerIndex);

}
