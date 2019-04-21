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
        dst.setBytes(dstIndex, this, index, length);
        return this;
    }

    @Override
    public HeapMemory getBytes(int index, byte[] dst, int dstIndex, int length) {
        ensureAccessible();
        ByteBuffer tmpBuf = buffer.duplicate();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
        return this;
    }

    @Override
    public HeapMemory setByte(int index, int value) {
        ensureAccessible();
        buffer.put(index(index), (byte) value);
        return this;
    }

    @Override
    public HeapMemory setShort(int index, int value) {
        ensureAccessible();
        buffer.putShort(index(index), (short) value);
        return this;
    }

    @Override
    public HeapMemory setShortLE(int index, int value) {
        ensureAccessible();
        buffer.putShort(index(index), Short.reverseBytes((short) value));
        return this;
    }

    @Override
    public HeapMemory setInt(int index, int value) {
        ensureAccessible();
        buffer.putInt(index(index), value);
        return this;
    }

    @Override
    public HeapMemory setIntLE(int index, int value) {
        ensureAccessible();
        buffer.putInt(index(index), Integer.reverseBytes(value));
        return this;
    }

    @Override
    public HeapMemory setLong(int index, long value) {
        ensureAccessible();
        buffer.putLong(index(index), value);
        return this;
    }

    @Override
    public HeapMemory setLongLE(int index, long value) {
        ensureAccessible();
        buffer.putLong(index(index), Long.reverseBytes(value));
        return this;
    }

    @Override
    public HeapMemory setBytes(int index, Memory src, int srcIndex, int length) {
        ensureAccessible();
        for (int i = 0; i < length; i++) {
            buffer.put(index++, src.getByte(srcIndex++));
        }
        return this;
    }

    public HeapMemory setBytes(int index, byte[] src, int srcIndex, int length) {
        ensureAccessible();
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
        checkIndex(index, length);
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
    public long memoryAddress() {
        ensureAccessible();
        if (InternalUnsafeHelper.isUnsafeAvailable()) {
            return InternalByteBufferHelper.directByteBufferAddress(buffer);
        }
        return 0;
    }

    @Override
    public void release() {
        // do nothing
    }

}
