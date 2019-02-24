package com.ardikars.common.memory;

class ReadWriteMemory extends AbstractMemory {

    ReadWriteMemory(long address, int capacity, int maxCapacity) {
        super(address, capacity, maxCapacity);
    }

    ReadWriteMemory(long address, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(address, capacity, maxCapacity, readerIndex, writerIndex);
    }

    @Override
    public ReadWriteMemory capacity(int newCapacity) {
        super.capacity = newCapacity;
        return this;
    }

    @Override
    public byte getByte(int index) {
        return InternalUnsafe._getByte(addr(index));
    }

    @Override
    public short getShort(int index) {
        return InternalUnsafe._getShort(addr(index));
    }

    @Override
    public short getShortLE(int index) {
        return InternalUnsafe._getShortLE(addr(index));
    }

    @Override
    public int getInt(int index) {
        return InternalUnsafe._getInt(addr(index));
    }

    @Override
    public int getIntLE(int index) {
        return InternalUnsafe._getIntLE(addr(index));
    }

    @Override
    public long getLong(int index) {
        return InternalUnsafe._getLong(addr(index));
    }

    @Override
    public long getLongLE(int index) {
        return InternalUnsafe._getLongLE(addr(index));
    }

    @Override
    public ReadWriteMemory getBytes(int index, Memory dst, int dstIndex, int length) {
        InternalUnsafe._getBytes(this, index, dst, dstIndex, length);
        return this;
    }

    @Override
    public ReadWriteMemory getBytes(int index, byte[] dst, int dstIndex, int length) {
        InternalUnsafe._getBytes(this, index, dst, dstIndex, length);
        return this;
    }

    @Override
    public ReadWriteMemory setByte(int index, int value) {
        InternalUnsafe._setByte(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setShort(int index, int value) {
        InternalUnsafe._setShort(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setShortLE(int index, int value) {
        InternalUnsafe._setShortLE(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setInt(int index, int value) {
        InternalUnsafe._setInt(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setIntLE(int index, int value) {
        InternalUnsafe._setIntLE(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setLong(int index, long value) {
        InternalUnsafe._setLong(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setLongLE(int index, long value) {
        InternalUnsafe._setLongLE(addr(index), value);
        return this;
    }

    @Override
    public ReadWriteMemory setBytes(int index, Memory src, int srcIndex, int length) {
        InternalUnsafe._setBytes(this, index, src, srcIndex, length);
        return this;
    }

    @Override
    public ReadWriteMemory setBytes(int index, byte[] src, int srcIndex, int length) {
        InternalUnsafe._setBytes(this, index, src, srcIndex, length);
        return this;
    }


    @Override
    public ReadWriteMemory copy(int index, int length) {
        checkIndex(index, length);
        long newAddress = InternalUnsafe._allocate(length);
        ReadWriteMemory memory = new ReadWriteMemory(newAddress, length, maxCapacity, readerIndex(), writerIndex());
        if (length != 0) {
            InternalUnsafe._copy(this, index, memory, length);
            memory.setInt(0, length);
        }
        return memory;
    }

    @Override
    public ReadWriteMemory slice(int index, int length) {
        return new SlicedReadWriteMemory(address, address + index, length, maxCapacity);
    }

    @Override
    public ReadWriteMemory duplicate() {
        ReadWriteMemory memory = copy(0, capacity);
        return memory;
    }
    
}
