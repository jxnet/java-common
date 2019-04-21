package com.ardikars.common.memory;

import java.nio.ByteBuffer;
import java.util.Arrays;

class HeapMemory extends AbstractNioMemory {

    public HeapMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity) {
        this(baseIndex, buffer, capacity, maxCapacity, 0, 0);
    }

    public HeapMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

    @Override
    public HeapMemory capacity(int newCapacity) {
        ensureAccessible();
        checkNewCapacity(newCapacity);

        int oldCapacity = capacity;
        ByteBuffer oldBuffer = buffer;
        ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity - baseIndex);
        if (newCapacity > oldCapacity) {
            oldBuffer.position(baseIndex).limit(oldBuffer.capacity() - baseIndex);
            newBuffer.position(baseIndex).limit(oldBuffer.capacity() - baseIndex);
            newBuffer.put(oldBuffer);
            newBuffer.clear();
        } else if (newCapacity < oldCapacity) {
            oldBuffer.position(baseIndex).limit(newCapacity - baseIndex);
            newBuffer.position(baseIndex).limit(newCapacity - baseIndex);
            newBuffer.put(oldBuffer);
            newBuffer.clear();
        }
        HeapMemory memory = new HeapMemory(baseIndex, newBuffer, newCapacity - baseIndex,
                maxCapacity - baseIndex > newCapacity - baseIndex ?
                        maxCapacity - baseIndex : newCapacity - baseIndex, readerIndex(), writerIndex());
        return memory;
    }

    @Override
    public HeapMemory getBytes(int index, Memory dst, int dstIndex, int length) {
        ensureAccessible();
        // index = baseIndex + index;
        checkDstIndex(index, length, dstIndex, dst.capacity());
        dst.setBytes(dstIndex, this, index, length);
        return this;
    }

    @Override
    public HeapMemory getBytes(int index, byte[] dst, int dstIndex, int length) {
        ensureAccessible();
        // index = baseIndex + index;
        checkDstIndex(index, length, dstIndex, dst.length);
        ByteBuffer tmpBuf = buffer.duplicate();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
        return this;
    }

    @Override
    public HeapMemory setByte(int index, int value) {
        ensureAccessible();
        buffer.put(baseIndex + index, (byte) value);
        return this;
    }

    @Override
    public HeapMemory setShort(int index, int value) {
        ensureAccessible();
        buffer.putShort(baseIndex + index, (short) value);
        return this;
    }

    @Override
    public HeapMemory setShortLE(int index, int value) {
        ensureAccessible();
        buffer.putShort(baseIndex + index, Short.reverseBytes((short) value));
        return this;
    }

    @Override
    public HeapMemory setInt(int index, int value) {
        ensureAccessible();
        buffer.putInt(baseIndex + index, value);
        return this;
    }

    @Override
    public HeapMemory setIntLE(int index, int value) {
        ensureAccessible();
        buffer.putInt(baseIndex + index, Integer.reverseBytes(value));
        return this;
    }

    @Override
    public HeapMemory setLong(int index, long value) {
        ensureAccessible();
        buffer.putLong(baseIndex + index, value);
        return this;
    }

    @Override
    public HeapMemory setLongLE(int index, long value) {
        ensureAccessible();
        buffer.putLong(baseIndex + index, Long.reverseBytes(value));
        return this;
    }

    @Override
    public HeapMemory setBytes(int index, Memory src, int srcIndex, int length) {
        ensureAccessible();
        // index = baseIndex + index;
        checkSrcIndex(index, length, srcIndex, src.capacity());
        for (int i = 0; i < length; i++) {
            buffer.put(index++, src.getByte(srcIndex++));
        }
        return this;
    }

    @Override
    public HeapMemory setBytes(int index, byte[] src, int srcIndex, int length) {
        ensureAccessible();
        // index = baseIndex + index;
        checkSrcIndex(index, length, srcIndex, src.length);
        byte[] bytesSrc = Arrays.copyOfRange(src, srcIndex, srcIndex + length);
        int pos = buffer.position();
        buffer.position(index);
        buffer.put(bytesSrc);
        buffer.position(pos);
        return this;
    }

    @Override
    public HeapMemory copy(int index, int length) {
        ensureAccessible();
        index = baseIndex + index;
        ByteBuffer copy = ByteBuffer.allocateDirect(length);
        HeapMemory newMemory = new HeapMemory(baseIndex, copy, length,
                maxCapacity > length ? maxCapacity : length, readerIndex(), writerIndex());
        newMemory.setBytes(0, this, index, length);
        return newMemory;
    }

    @Override
    public HeapMemory slice(int index, int length) {
        ensureAccessible();
        return new SlicedHeapMemory(baseIndex + index, buffer, length, maxCapacity(),
                readerIndex() - index, writerIndex() - index);
    }

    @Override
    public HeapMemory duplicate() {
        ensureAccessible();
        return new HeapMemory(baseIndex, buffer, capacity(), maxCapacity(), readerIndex(), writerIndex());
    }

    @Override
    public ByteBuffer nioBuffer() {
        return buffer;
    }

    @Override
    public void release() {
        // do noting here
    }

    @Override
    public long memoryAddress() {
        return 0; // has no memory address
    }

}
