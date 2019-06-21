package com.ardikars.common.memory;

import com.ardikars.common.memory.internal.Unsafe;
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
        assert allocator instanceof DefaultMemoryAllocator;
        Memory memory = allocator.allocate(8);
        assert memory.nioBuffer().isDirect();
        assert memory.isDirect() || !memory.isDirect();
        memory.release();
    }

    @Test
    public void pooledAllocator() {
        MemoryAllocator allocator = Memories.allocator(5, 7, 10);
        for (int i = 0; i < 10; i++) {
            Memory memory = allocator.allocate(i+1);
            memory.release();
        }
        allocator.close();
    }

    @Test
    public void pooledAllocatorFull() {
        MemoryAllocator allocator = Memories.allocator(5, 7, 10);
        for (int i = 0; i < 10; i++) {
            Memory memory = allocator.allocate(i+1);
            if (i > 6) {
                if (!Unsafe.HAS_UNSAFE) {
                    assert memory instanceof ByteBuf;
                }
            }
        }
        allocator.close();
    }

}
