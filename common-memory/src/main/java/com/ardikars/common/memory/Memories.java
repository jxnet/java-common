package com.ardikars.common.memory;

import com.ardikars.common.memory.internal.ByteBufferHelper;

import java.nio.ByteBuffer;

public final class Memories {

    public static MemoryAllocator allocator() {
        return new DefaultMemoryAllocator();
    }

    public static Memory wrap(long memoryAddress, int size) {
        return wrap(memoryAddress, size, true);
    }

    public static Memory wrap(long memoryAddress, int size, boolean checking) {
        if (checking) {
            return new CheckedMemory(memoryAddress, size, size);
        }
        return new UncheckedMemory(memoryAddress, size, size);
    }

    public static Memory wrap(ByteBuffer buffer) throws UnsupportedOperationException {
        return wrap(buffer, true);
    }

    public static Memory wrap(ByteBuffer buffer, boolean checking) throws UnsupportedOperationException {
        if (!buffer.isDirect()) {
            throw new UnsupportedOperationException();
        }
        int capacity = buffer.capacity();
        long address = ByteBufferHelper.directByteBufferAddress(buffer);
        if (checking) {
            return new CheckedMemory(address, capacity, capacity);
        }
        return new UncheckedMemory(address, capacity, capacity);
    }

}
