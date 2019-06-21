package com.ardikars.common.memory;

import com.ardikars.common.memory.internal.ByteBufferHelper;
import com.ardikars.common.util.Hexs;
import com.ardikars.common.util.Validate;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used for allocating memory buffer or wrapping buffer.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public final class Memories {

    static Map<Integer, Queue<PooledMemory>> POOLS;

    /**
     * Get default memory allocator.
     * @return returns default {@link MemoryAllocator}.
     */
    public static MemoryAllocator allocator() {
        return new DefaultMemoryAllocator();
    }

    /**
     * Get pooled memory allocator.
     * @param poolSize pool size.
     * @param maxPoolSize maximum pool size.
     * @param maxMemoryCapacity memory capacity per buffer.
     * @return returns pooled {@link MemoryAllocator}.
     */
    public synchronized static MemoryAllocator allocator(int poolSize, int maxPoolSize, int maxMemoryCapacity) {
        if (POOLS == null) {
            POOLS = new ConcurrentHashMap<Integer, Queue<PooledMemory>>();
        }
        return new PooledMemoryAllocator(poolSize, maxPoolSize, maxMemoryCapacity);
    }

    /**
     * Wrap direct memory address into {@link Memory} object with bounds checking.
     * @param memoryAddress memory address.
     * @param size size of memory.
     * @return returns {@link Memory}.
     */
    public static Memory wrap(long memoryAddress, int size) {
        return wrap(memoryAddress, size, true);
    }

    /**
     * Wrap direct memory address into {@link Memory} object.
     * @param memoryAddress memory address.
     * @param size size of memory.
     * @param checking if true it will do bounds checking for every get/set method, false will not bounds checking.
     * @return returns {@link Memory}.
     */
    public static Memory wrap(long memoryAddress, int size, boolean checking) {
        Validate.notIllegalArgument(size > 0,
                new IllegalArgumentException(String.format("size: %d (expected: > 0)", size)));
        if (checking) {
            return new CheckedMemory(memoryAddress, size, size);
        }
        return new UncheckedMemory(memoryAddress, size, size);
    }

    /**
     * Wrap direct {@link ByteBuffer} into {@link Memory} with bounds checking.
     * @param buffer direct buffer.
     * @return returns {@link Memory}.
     * @throws UnsupportedOperationException is't not direct buffer.
     */
    public static Memory wrap(ByteBuffer buffer) throws UnsupportedOperationException {
        return wrap(buffer, true);
    }

    /**
     * Wrap direct {@link ByteBuffer} into {@link Memory}.
     * @param buffer direct buffer.
     * @param checking if true it will do bounds checking for every get/set method, false will not bounds checking.
     * @return returns {@link Memory}.
     * @throws UnsupportedOperationException is't not direct buffer.
     */
    public static Memory wrap(ByteBuffer buffer, boolean checking) throws UnsupportedOperationException {
        Validate.notIllegalArgument(buffer != null,
                new IllegalArgumentException("buffer: null (expected: non null)"));
        Validate.notIllegalArgument(buffer.isDirect(),
                new IllegalArgumentException(String.format("buffer.isDirect(): %b (expected: direct buffer)", buffer.isDirect())));
        int capacity = buffer.capacity();
        long address = ByteBufferHelper.directByteBufferAddress(buffer);
        if (checking) {
            return new CheckedMemory(address, capacity, capacity);
        }
        return new UncheckedMemory(address, capacity, capacity);
    }

    /**
     * Wrap hex string into {@link Memory}.
     * @param hexStream hex string.
     * @return returns {@link Memory}.
     */
    public static Memory wrap(CharSequence hexStream) {
        return wrap(hexStream, true);
    }

    /**
     * Wrap hex string into {@link Memory}.
     * @param hexStream hex string.
     * @param checking if true it will do bounds checking for every get/set method, false will not bounds checking.
     * @return returns {@link Memory}.
     * @throws IllegalArgumentException invalid hex characters.
     */
    public static Memory wrap(CharSequence hexStream, boolean checking) {
        Validate.notIllegalArgument(hexStream != null,
                new IllegalArgumentException(String.format("hexStream: null (expected: non null)")));
        byte[] data = Hexs.parseHex(hexStream.toString());
        Memory memory = allocator().allocate(data.length, checking);
        memory.setBytes(0, data);
        return memory;
    }

    static void offer(Memory memory) {
        POOLS.get(memory.maxCapacity()).offer(new PooledMemory(memory));
    }

    static Memory poll(int maxCapacity) {
        PooledMemory pooledMemory = POOLS.get(maxCapacity).poll();
        if (pooledMemory != null) {
            return pooledMemory.get();
        }
        return null;
    }

}
