package com.ardikars.common.memory;

import java.nio.ByteBuffer;

public final class Memories {

    public static MemoryAllocator allocator() {
        if (InternalUnsafeHelper.isUnsafeAvailable()) {
            return nativeAllocator();
        } else {
            return directAllocator();
        }
    }

    public static MemoryAllocator allocator(boolean direct) {
        if (direct) {
            return allocator();
        }
        return heapAllocator();
    }

    public static MemoryAllocator nativeAllocator() {
        return new NativeMemoryAllocator();
    }

    public static MemoryAllocator directAllocator() {
        return new DirectMemoryAllocator();
    }

    public static MemoryAllocator heapAllocator() {
        return new HeapMemoryAllocator();
    }

    public static Memory wrap(long memoryAddress, int size) {
        return new NativeMemory(memoryAddress, size, size);
    }

    public static Memory wrap(ByteBuffer memoryBuffer, boolean forceWrap) throws UnsupportedOperationException {
        int capacity = memoryBuffer.capacity();
        if (!memoryBuffer.isDirect()) {
            if (forceWrap) {
                ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);
                memoryBuffer.position(0);
                memoryBuffer.limit(capacity);
                buffer.put(memoryBuffer);
                return new DirectMemory(0, memoryBuffer, capacity, capacity);
            } else {
                throw new UnsupportedOperationException(
                        String.format("ByteBuffer.isDirect(): %s", memoryBuffer.isDirect()));
            }
        } else {
            return new DirectMemory(0, memoryBuffer, capacity, capacity);
        }
    }

}
