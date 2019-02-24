package com.ardikars.common.memory;

class SlicedReadWriteMemory extends ReadWriteMemory {

    private final long baseAddress;

    SlicedReadWriteMemory(long baseAddress, long address, int capacity, int maxCapacity) {
        super(address, capacity, maxCapacity);
        this.baseAddress = baseAddress;
    }

    SlicedReadWriteMemory(long baseAddress, long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(address, capacity, maxCapacity, readerIndex, writerIndex);
        this.baseAddress = baseAddress;
    }

    @Override
    public void release() {
        InternalUnsafe.free(baseAddress);
    }

}
