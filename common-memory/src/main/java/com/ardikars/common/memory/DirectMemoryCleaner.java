package com.ardikars.common.memory;

import java.nio.ByteBuffer;

interface DirectMemoryCleaner {

    void clean(ByteBuffer memory);

}
