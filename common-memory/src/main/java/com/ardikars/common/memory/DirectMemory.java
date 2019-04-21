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
        // index = baseIndex + index;
        checkDstIndex(index, length, dstIndex, dst.capacity());
        dst.setBytes(dstIndex, this, index, length);
        return this;
    }

    @Override
    public DirectMemory getBytes(int index, byte[] dst, int dstIndex, int length) {
        ensureAccessible();
        // index = baseIndex + index;
        checkDstIndex(index, length, dstIndex, dst.length);
        ByteBuffer tmpBuf = buffer.duplicate();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
        return this;
    }

    @Override
    public DirectMemory setByte(int index, int value) {
        ensureAccessible();
        buffer.put(baseIndex + index, (byte) value);
        return this;
    }

    @Override
    public DirectMemory setShort(int index, int value) {
        ensureAccessible();
        buffer.putShort(baseIndex + index, (short) value);
        return this;
    }

    @Override
    public DirectMemory setShortLE(int index, int value) {
        ensureAccessible();
        buffer.putShort(baseIndex + index, Short.reverseBytes((short) value));
        return this;
    }

    @Override
    public DirectMemory setInt(int index, int value) {
        ensureAccessible();
        buffer.putInt(baseIndex + index, value);
        return this;
    }

    @Override
    public DirectMemory setIntLE(int index, int value) {
        ensureAccessible();
        buffer.putInt(baseIndex + index, Integer.reverseBytes(value));
        return this;
    }

    @Override
    public DirectMemory setLong(int index, long value) {
        ensureAccessible();
        buffer.putLong(baseIndex + index, value);
        return this;
    }

    @Override
    public DirectMemory setLongLE(int index, long value) {
        ensureAccessible();
        buffer.putLong(baseIndex + index, Long.reverseBytes(value));
        return this;
    }

    @Override
    public DirectMemory setBytes(int index, Memory src, int srcIndex, int length) {
        ensureAccessible();
        // index = baseIndex + index;
        checkSrcIndex(index, length, srcIndex, src.capacity());
        for (int i = 0; i < length; i++) {
            buffer.put(index++, src.getByte(srcIndex++));
        }
        return this;
    }

    @Override
    public DirectMemory setBytes(int index, byte[] src, int srcIndex, int length) {
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
    public DirectMemory copy(int index, int length) {
        ensureAccessible();
        index = baseIndex + index;
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
    public void release() {
        if (!freed) {
            CLEANER.clean(buffer);
            freed = true;
        }
    }

    @Override
    public long memoryAddress() {
        ensureAccessible();
        if (InternalUnsafeHelper.isUnsafeAvailable()) {
            return InternalByteBufferHelper.directByteBufferAddress(buffer);
        }
        return 0;
    }

}
