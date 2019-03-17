package com.ardikars.common.memory;

import java.nio.ByteBuffer;

class NativeMemory extends AbstractMemory {

    long address;

    NativeMemory(long address, int capacity, int maxCapacity) {
        super(capacity, maxCapacity);
        this.address = address;
    }

    NativeMemory(long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(capacity, maxCapacity, readerIndex, writerIndex);
        this.address = address;
    }

    @Override
    public NativeMemory capacity(int newCapacity) {
        ensureAccessible();
        checkNewCapacity(newCapacity);
        this.address = InternalUnsafeOperation._reallocate(this, newCapacity);
        this.capacity = newCapacity;
        this.maxCapacity = maxCapacity > newCapacity ? maxCapacity : newCapacity;
        return this;
    }

    @Override
    public byte getByte(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getByte(addr(index));
    }

    @Override
    public short getShort(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getShort(addr(index));
    }

    @Override
    public short getShortLE(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getShortLE(addr(index));
    }

    @Override
    public int getInt(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getInt(addr(index));
    }

    @Override
    public int getIntLE(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getIntLE(addr(index));
    }

    @Override
    public long getLong(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getLong(addr(index));
    }

    @Override
    public long getLongLE(int index) {
        ensureAccessible();
        return InternalUnsafeOperation._getLongLE(addr(index));
    }

    @Override
    public NativeMemory getBytes(int index, Memory dst, int dstIndex, int length) {
        ensureAccessible();
        InternalUnsafeOperation._getBytes(this, index, dst, dstIndex, length);
        return this;
    }

    @Override
    public NativeMemory getBytes(int index, byte[] dst, int dstIndex, int length) {
        ensureAccessible();
        InternalUnsafeOperation._getBytes(this, index, dst, dstIndex, length);
        return this;
    }

    @Override
    public NativeMemory setByte(int index, int value) {
        ensureAccessible();
        InternalUnsafeOperation._setByte(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setShort(int index, int value) {
        ensureAccessible();
        InternalUnsafeOperation._setShort(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setShortLE(int index, int value) {
        ensureAccessible();
        InternalUnsafeOperation._setShortLE(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setInt(int index, int value) {
        ensureAccessible();
        InternalUnsafeOperation._setInt(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setIntLE(int index, int value) {
        ensureAccessible();
        InternalUnsafeOperation._setIntLE(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setLong(int index, long value) {
        ensureAccessible();
        InternalUnsafeOperation._setLong(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setLongLE(int index, long value) {
        ensureAccessible();
        InternalUnsafeOperation._setLongLE(addr(index), value);
        return this;
    }

    @Override
    public NativeMemory setBytes(int index, Memory src, int srcIndex, int length) {
        ensureAccessible();
        if (src instanceof DirectMemory) {
            long baseAddress = memoryAddress();
            for (int i = 0; i < length; i++) {
                InternalUnsafeOperation._setByte(baseAddress++, src.getByte(srcIndex++));
            }
        } else if (src instanceof NativeMemory){
            InternalUnsafeOperation._setBytes(this, index, src, srcIndex, length);
        }
        return this;
    }

    @Override
    public NativeMemory setBytes(int index, byte[] src, int srcIndex, int length) {
        ensureAccessible();
        InternalUnsafeOperation._setBytes(this, index, src, srcIndex, length);
        return this;
    }

    @Override
    public long memoryAddress() {
        ensureAccessible();
        return address;
    }

    @Override
    public NativeMemory copy(int index, int length) {
        ensureAccessible();
        checkIndex(index, length);
        long newAddress = InternalUnsafeOperation._allocate(length);
        NativeMemory memory = new NativeMemory(newAddress, length, maxCapacity, readerIndex(), writerIndex());
        if (length != 0) {
            memory.setBytes(0, this, index, length);
        }
        return memory;
    }

    @Override
    public NativeMemory slice(int index, int length) {
        ensureAccessible();
        return new SlicedNativeMemory(address, capacity, address + index, length, maxCapacity, readerIndex() - index, writerIndex() - index);
    }

    @Override
    public NativeMemory duplicate() {
        ensureAccessible();
        NativeMemory memory = new NativeMemory(address, capacity, maxCapacity, readerIndex(), writerIndex());
        return memory;
    }

    @Override
    public ByteBuffer nioBuffer() {
        return InternalUnsafeOperation._wrap(memoryAddress(), capacity);
    }

    @Override
    public void release() {
        if (!freed) {
            InternalUnsafeHelper.getUnsafe().freeMemory(memoryAddress());
            freed = true;
        }
    }

    final long addr(int index) {
        return address + index;
    }

}
