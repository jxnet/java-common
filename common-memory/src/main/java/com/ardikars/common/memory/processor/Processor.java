package com.ardikars.common.memory.processor;

public interface Processor {

    byte getBitByte(int index, int length);

    short getBitUnsignedByte(int index, int length);

    short getBitShort(int index, int length);

    short getBitShortLE(int index, int length);

    int getBitUnsignedShort(int index, int length);

    int getBitUnsignedShortLE(int index, int length);

    int getBitInt(int index, int length);

    int getBitIntLE(int index, int length);

    long getBitUnsignedInt(int index, int length);

    long getBitUnsignedIntLE(int index, int length);

    float getBitFloat(int index, int length);

    float getBitFloatLE(int index, int length);

    long getBitLong(int index, int length);

    long getBitLongLE(int index, int length);

    double getBitDouble(int index, int length);

    double getBitDoubleLE(int index, int length);

}
