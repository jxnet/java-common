package com.ardikars.common.memory.accessor;

import java.nio.ByteBuffer;

public interface MemoryAccessor {

    long allocate(int size);

    long reallocate(long addr, int size);

    void deallocate(long addr);

    ByteBuffer nioBuffer(long addr, int size);

    byte getByte(long addr);

    short getShort(long addr);

    short getShortLE(long addr);

    int getInt(long addr);

    int getIntLE(long addr);

    long getLong(long addr);

    long getLongLE(long addr);

    void getBytes(long srcAddr, int index, long dstAddr, int dstIndex, int size);

    void getBytes(long srcAddr, int index, byte[] dst, int dstIndex, int size);

    void setByte(long addr, int val);

    void setShort(long addr, int val);

    void setShortLE(long addr, int val);

    void setInt(long addr, int val);

    void setIntLE(long addr, int val);

    void setLong(long addr, long val);

    void setLongLE(long addr, long val);

    void setBytes(long dstAddr, int index, long srcAddr, int srcIndex, int size);

    void setBytes(long dstAddr, int index, byte[] src, int srcIndex, int size);

}
