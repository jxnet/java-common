package com.ardikars.common.memory;

import com.ardikars.common.memory.processor.Processor;

interface MemoryProcessor {

    Processor getByteProcessor(int index);

    Processor getShortProcessor(int index);

    Processor getIntegerProcessor(int index);

    Processor getLongProcessor(int index);

    Processor readByteProcessor();

    Processor readShortProcessor();

    Processor readIntegerProcessor();

    Processor readLongProcessor();

}
