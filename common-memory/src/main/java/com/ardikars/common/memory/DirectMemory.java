package com.ardikars.common.memory;

import com.ardikars.common.util.Platforms;

import java.nio.ByteBuffer;
import java.util.Arrays;

class DirectMemory extends AbstractNioMemory {

    private static final DirectMemoryCleaner CLEANER = Platforms.getJavaMojorVersion() < 9 ?
            new DirectMemoryCleanerJdk6() : new DirectMemoryCleanerJdk9();

    public DirectMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity) {
        this(baseIndex, buffer, capacity, maxCapacity, 0, 0);
    }

    public DirectMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

    @Override
    public DirectMemory capacity(int newCapacity) {
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
        DirectMemory memory = new DirectMemory(baseIndex, newBuffer, newCapacity - baseIndex,
                maxCapacity - baseIndex > newCapacity - baseIndex ?
                maxCapacity - baseIndex : newCapacity - baseIndex, readerIndex(), writerIndex());
        CLEANER.clean(oldBuffer);
        return memory;
    }

    @Override
    public DirectMemory getBytes(int index, Memory dst, int dstIndex, int length) {
        ensureAccessible();
        dst.setBytes(dstIndex, this, index, length);
        return this;
    }

    @Override
    public DirectMemory getBytes(int index, byte[] dst, int dstIndex, int length) {
        ensureAccessible();
        ByteBuffer tmpBuf = buffer.duplicate();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
        return this;
    }

    @Override
    public DirectMemory setByte(int index, int value) {
        ensureAccessible();
        buffer.put(index(index), (byte) value);
        return this;
    }

    @Override
    public DirectMemory setShort(int index, int value) {
        ensureAccessible();
        buffer.putShort(index(index), (short) value);
        return this;
    }

    @Override
    public DirectMemory setShortLE(int index, int value) {
        ensureAccessible();
        buffer.putShort(index(index), Short.reverseBytes((short) value));
        return this;
    }

    @Override
    public DirectMemory setInt(int index, int value) {
        ensureAccessible();
        buffer.putInt(index(index), value);
        return this;
    }

    @Override
    public DirectMemory setIntLE(int index, int value) {
        ensureAccessible();
        buffer.putInt(index(index), Integer.reverseBytes(value));
        return this;
    }

    @Override
    public DirectMemory setLong(int index, long value) {
        ensureAccessible();
        buffer.putLong(index(index), value);
        return this;
    }

    @Override
    public DirectMemory setLongLE(int index, long value) {
        ensureAccessible();
        buffer.putLong(index(index), Long.reverseBytes(value));
        return this;
    }

    @Override
    public DirectMemory setBytes(int index, Memory src, int srcIndex, int length) {
        ensureAccessible();
        for (int i = 0; i < length; i++) {
            buffer.put(index++, src.getByte(srcIndex++));
        }
        return this;
    }

    public DirectMemory setBytes(int index, byte[] src, int srcIndex, int length) {
        ensureAccessible();
        byte[] bytesSrc = Arrays.copyOfRange(src, srcIndex, srcIndex + length);
        int pos = buffer.position();
        buffer.position(index);
        buffer.put(bytesSrc);
        buffer.position(pos);
        return this;
    }


    @Override
    public DirectMemory copy(int index, int length) {
        ensureAccessible();
        checkIndex(index, length);
        ByteBuffer copy = ByteBuffer.allocateDirect(length);
        DirectMemory newMemory = new DirectMemory(baseIndex, copy, length,
                maxCapacity > length ? maxCapacity : length, readerIndex(), writerIndex());
        newMemory.setBytes(0, this, index, length);
        return newMemory;
    }

    @Override
    public DirectMemory slice(int index, int length) {
        ensureAccessible();
        return new SlicedDirectMemory(baseIndex + index, buffer, length, maxCapacity(),
                readerIndex() - index, writerIndex() - index);
    }

    @Override
    public DirectMemory duplicate() {
        ensureAccessible();
        return new DirectMemory(baseIndex, buffer, capacity(), maxCapacity(), readerIndex(), writerIndex());
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
        if (!freed) {
            CLEANER.clean(buffer);
            freed = true;
        }
    }

}
