package com.ardikars.common.memory;

import java.nio.ByteBuffer;

class SlicedCheckedMemory extends CheckedMemory {

    private final long baseAddress;
    private final int baseCapacity;

    public SlicedCheckedMemory(long baseAddress, int baseCapacity, long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(address, capacity, maxCapacity, readerIndex, writerIndex);
        this.baseAddress = baseAddress;
        this.baseCapacity = baseCapacity;
    }

    @Override
    public ByteBuffer nioBuffer() {
        ensureAccessible(0, baseCapacity);
        return ACCESSOR.nioBuffer(baseAddress, baseCapacity);
    }

    @Override
    public void release() {
        if (!freed) {
            ACCESSOR.deallocate(baseAddress);
        }
    }

}
