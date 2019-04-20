package com.ardikars.common.memory;

import java.nio.ByteBuffer;

abstract class AbstractNioMemory extends AbstractMemory {

    int baseIndex;

    ByteBuffer buffer;

    AbstractNioMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity) {
        super(capacity, maxCapacity);
        this.baseIndex = baseIndex;
        this.buffer = buffer;
    }

    AbstractNioMemory(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(capacity, maxCapacity, readerIndex, writerIndex);
        this.baseIndex = baseIndex;
        this.buffer = buffer;
    }

    @Override
    public byte getByte(int index) {
        ensureAccessible();
        return buffer.get(baseIndex + index);
    }

    @Override
    public short getShort(int index) {
        ensureAccessible();
        return buffer.getShort(baseIndex + index);
    }

    @Override
    public short getShortLE(int index) {
        ensureAccessible();
        return Short.reverseBytes(buffer.getShort(baseIndex + index));
    }

    @Override
    public int getInt(int index) {
        ensureAccessible();
        return buffer.getInt(baseIndex + index);
    }

    @Override
    public int getIntLE(int index) {
        ensureAccessible();
        return Integer.reverseBytes(buffer.getInt(baseIndex + index));
    }

    @Override
    public long getLong(int index) {
        ensureAccessible();
        return buffer.getLong(baseIndex + index);
    }

    @Override
    public long getLongLE(int index) {
        ensureAccessible();
        return Long.reverseBytes(buffer.getLong(baseIndex + index));
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

    protected void checkSrcIndex(int index, int length, int srcIndex, int srcCapacity) {
        checkIndex(index, length);
        if (checkBounds) {
            checkRangeBounds(srcIndex, length, srcCapacity);
        }
    }

    protected void checkDstIndex(int index, int length, int dstIndex, int dstCapacity) {
        checkIndex(index, length);
        if (checkBounds) {
            checkRangeBounds(dstIndex, length, dstCapacity);
        }
    }

    private void checkRangeBounds(final int index, final int fieldLength, final int capacity) {
        if (isOutOfBounds(index, fieldLength, capacity)) {
            throw new IndexOutOfBoundsException(String.format(
                    "index: %d, length: %d (expected: range(0, %d))", index, fieldLength, capacity));
        }
    }

}
