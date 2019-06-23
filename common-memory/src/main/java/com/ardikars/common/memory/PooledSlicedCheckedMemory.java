package com.ardikars.common.memory;

class PooledSlicedCheckedMemory extends SlicedCheckedMemory {

    PooledSlicedCheckedMemory(long baseAddress, int baseCapacity, long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseAddress, baseCapacity, address, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
