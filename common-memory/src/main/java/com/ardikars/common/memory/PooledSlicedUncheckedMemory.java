package com.ardikars.common.memory;

class PooledSlicedUncheckedMemory extends SlicedUncheckedMemory {

    PooledSlicedUncheckedMemory(long baseAddress, int baseCapacity, long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseAddress, baseCapacity, address, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
