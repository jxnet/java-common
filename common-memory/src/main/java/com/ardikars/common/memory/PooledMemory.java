package com.ardikars.common.memory;

class PooledMemory {

    private Memory memory;

    public PooledMemory(Memory referent) {
        this.memory = referent;
    }

    public Memory get() {
        return memory;
    }

}
