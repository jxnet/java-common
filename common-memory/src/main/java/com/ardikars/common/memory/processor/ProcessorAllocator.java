package com.ardikars.common.memory.processor;

public interface ProcessorAllocator {

    Processor bitByte(byte value);

    Processor bitShort(short value);

    Processor bitInteger(int value);

    Processor bitLong(long value);

}
