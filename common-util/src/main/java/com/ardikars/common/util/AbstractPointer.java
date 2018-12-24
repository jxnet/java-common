package com.ardikars.common.util;

public abstract class AbstractPointer implements Pointer {

    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws Exception {
        // do noting
    }

}
