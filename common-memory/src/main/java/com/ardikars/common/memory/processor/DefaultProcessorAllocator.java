package com.ardikars.common.memory.processor;

public class DefaultProcessorAllocator implements ProcessorAllocator {

    @Override
    public Processor bitByte(byte value) {
        return new ByteProcessor(value);
    }

    @Override
    public Processor bitShort(short value) {
        return new ShortProcessor(value);
    }

    @Override
    public Processor bitInteger(int value) {
        return new IntegerProcessor(value);
    }

    @Override
    public Processor bitLong(long value) {
        return new LongProcessor(value);
    }

}
