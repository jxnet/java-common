package com.ardikars.common.memory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface Memory {

    int capacity();

    Memory capacity(int newCapacity);

    int maxCapacity();

    int readerIndex();

    Memory readerIndex(int readerIndex);

    int writerIndex();

    Memory writerIndex(int writerIndex);

    Memory setIndex(int readerIndex, int writerIndex);

    int readableBytes();

    int writableBytes();

    int maxWritableBytes();

    boolean isReadable();

    boolean isReadable(int numBytes);

    boolean isWritable();

    boolean isWritable(int numBytes);

    Memory clear();

    Memory markReaderIndex();

    Memory resetReaderIndex();

    Memory markWriterIndex();

    Memory resetWriterIndex();

    Memory ensureWritable(int minWritableBytes);

    boolean getBoolean(int index);

    byte getByte(int index);

    short getUnsignedByte(int index);

    short getShort(int index);

    short getShortLE(int index);

    int getUnsignedShort(int index);

    int getUnsignedShortLE(int index);

    int getInt(int index);

    int getIntLE(int index);

    long getUnsignedInt(int index);

    long getUnsignedIntLE(int index);

    long getLong(int index);

    long getLongLE(int index);

    float getFloat(int index);

    float getFloatLE(int index);

    double getDouble(int index);

    double getDoubleLE(int index);

    Memory getBytes(int index, Memory dst);

    Memory getBytes(int index, Memory dst, int length);

    Memory getBytes(int index, Memory dst, int dstIndex, int length);

    Memory getBytes(int index, byte[] dst);

    Memory getBytes(int index, byte[] dst, int dstIndex, int length);

    CharSequence getCharSequence(int index, int length, Charset charset);

    Memory setBoolean(int index, boolean value);

    Memory setByte(int index, int value);

    Memory setShort(int index, int value);

    Memory setShortLE(int index, int value);

    Memory setInt(int index, int value);

    Memory setIntLE(int index, int value);

    Memory setLong(int index, long value);

    Memory setLongLE(int index, long value);

    Memory setFloat(int index, float value);

    Memory setFloatLE(int index, float value);

    Memory setDouble(int index, double value);

    Memory setDoubleLE(int index, double value);

    Memory setBytes(int index, Memory src);

    Memory setBytes(int index, Memory src, int length);

    Memory setBytes(int index, Memory src, int srcIndex, int length);

    Memory setBytes(int index, byte[] src);

    Memory setBytes(int index, byte[] src, int srcIndex, int length);

    Memory setCharSequence(int index, CharSequence sequence, Charset charset);

    boolean readBoolean();

    byte readByte();

    short readUnsignedByte();

    short readShort();

    short readShortLE();

    int readUnsignedShort();

    int readUnsignedShortLE();

    int readInt();

    int readIntLE();

    long readUnsignedInt();

    long readUnsignedIntLE();

    long readLong();

    long readLongLE();

    float readFloat();

    float readFloatLE();

    double readDouble();

    double readDoubleLE();

    Memory readBytes(Memory dst);

    Memory readBytes(Memory dst, int length);

    Memory readBytes(Memory dst, int dstIndex, int length);

    Memory readBytes(byte[] dst);

    Memory readBytes(byte[] dst, int dstIndex, int length);

    Memory skipBytes(int length);

    CharSequence readCharSequence(int length, Charset charset);

    Memory writeBoolean(boolean value);

    Memory writeByte(int value);

    Memory writeShort(int value);

    Memory writeShortLE(int value);

    Memory writeInt(int value);

    Memory writeIntLE(int value);

    Memory writeLong(long value);

    Memory writeLongLE(long value);

    Memory writeFloat(float value);

    Memory writeFloatLE(float value);

    Memory writeDouble(double value);

    Memory writeDoubleLE(double value);

    Memory writeBytes(Memory src);

    Memory writeBytes(Memory src, int length);

    Memory writeBytes(Memory src, int srcIndex, int length);

    Memory writeBytes(byte[] src);

    Memory writeBytes(byte[] src, int srcIndex, int length);

    Memory writeCharSequence(CharSequence sequence, Charset charset);

    Memory copy();

    Memory copy(int index, int length);

    Memory slice();

    Memory slice(int index, int length);

    Memory duplicate();

    ByteBuffer nioBuffer();

    long memoryAddress();

    void release();

}
