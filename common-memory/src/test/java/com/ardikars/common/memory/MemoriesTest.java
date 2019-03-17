package com.ardikars.common.memory;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemoriesTest {

    @Test
    public void defaultAllocator() {
        MemoryAllocator allocator = Memories.allocator();
        assert allocator instanceof DirectMemoryAllocator || allocator instanceof NativeMemoryAllocator;
        Memory memory = allocator.allocate(8);
        memory.release();
    }

    @Test
    public void nativeAllocator() {
        MemoryAllocator allocator = Memories.nativeAllocator();
        assert allocator instanceof NativeMemoryAllocator;
        Memory memory = allocator.allocate(8);
        memory.release();
    }

    @Test
    public void directAllocator() {
        MemoryAllocator allocator = Memories.directAllocator();
        assert allocator instanceof DirectMemoryAllocator;
        Memory memory = allocator.allocate(8);
        memory.release();
    }

}
