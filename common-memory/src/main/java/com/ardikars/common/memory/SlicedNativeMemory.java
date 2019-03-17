package com.ardikars.common.memory;

import java.nio.ByteBuffer;

class SlicedNativeMemory extends NativeMemory {

    private final long baseAddress;
    private final int baseCapacity;

    public SlicedNativeMemory(long baseAddress, int baseCapacity, long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(address, capacity, maxCapacity, readerIndex, writerIndex);
        this.baseAddress = baseAddress;
        this.baseCapacity = baseCapacity;
    }

    @Override
    public ByteBuffer nioBuffer() {
        return InternalUnsafeOperation._wrap(baseAddress, baseCapacity);
    }

    @Override
    public void release() {
        if (!freed) {
            InternalUnsafeOperation.free(baseAddress);
        }
    }

}
