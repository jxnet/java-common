package com.ardikars.common.memory;

/**
 * The default {@link MemoryAllocator} for allocating {@link Memory} buffer's.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
final class DefaultMemoryAllocator implements MemoryAllocator {

    @Override
    public Memory allocate(int capacity) {
        return allocate(capacity, 0, 0, 0, true);
    }

    @Override
    public Memory allocate(int capacity, boolean checking) {
        return allocate(capacity, capacity, 0, 0, checking);
    }

    @Override
    public Memory allocate(int capacity, int maxCapacity) {
        return allocate(capacity, maxCapacity, 0, 0, true);
    }

    @Override
    public Memory allocate(int capacity, int maxCapacity, boolean checking) {
        return allocate(capacity, maxCapacity, 0, 0, checking);
    }

    @Override
    public Memory allocate(int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        return allocate(capacity, maxCapacity, readerIndex, writerIndex, true);
    }

    @Override
    public Memory allocate(int capacity, int maxCapacity, int readerIndex, int writerIndex, boolean checking) {
        long address = AbstractMemory.ACCESSOR.allocate(capacity);
        if (checking) {
            return new CheckedMemory(address, capacity, maxCapacity, readerIndex, writerIndex);
        }
        return new UncheckedMemory(address, capacity, maxCapacity, readerIndex, writerIndex);
    }

}
