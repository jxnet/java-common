package com.ardikars.common.memory;

class SlicedNativeMemory extends NativeMemory {

    private final long baseAddress;

    public SlicedNativeMemory(long baseAddress, long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(address, capacity, maxCapacity, readerIndex, writerIndex);
        this.baseAddress = baseAddress;
    }

    @Override
    public void release() {
        if (!freed) {
            InternalUnsafeOperation.free(baseAddress);
        }
    }

}
