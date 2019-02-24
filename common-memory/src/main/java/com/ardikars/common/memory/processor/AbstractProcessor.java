package com.ardikars.common.memory.processor;

import com.ardikars.common.memory.InternalUnsafe;

abstract class AbstractProcessor implements Processor {

    static final boolean BIG_ENDIAN_NATIVE_ORDER = InternalUnsafe.BIG_ENDIAN_NATIVE_ORDER;

    final int capacity;

    AbstractProcessor(int capacity) {
        this.capacity = capacity;
    }

    final int calculateSize(int index, int length) {
        return capacity - length - index;
    }

    static short reverseShort(short value) {
        return BIG_ENDIAN_NATIVE_ORDER ? value : Short.reverseBytes(value);
    }

    static int reverseInt(int value) {
        return BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value);
    }

    static long reverseLong(long value) {
        return BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value);
    }

}
