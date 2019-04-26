package com.ardikars.common.memory.internal;

import com.ardikars.common.memory.accessor.MemoryAccessor;
import com.ardikars.common.memory.accessor.MemoryAccessors;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.nio.ByteBuffer;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ByteBufferHelperTest {

    private boolean hasUnsafe = UnsafeHelper.isUnsafeAvailable();
    private MemoryAccessor accessor = MemoryAccessors.memoryAccessor();

    private ByteBuffer buffer;

    @Before
    public void before() {
        buffer = ByteBuffer.allocateDirect(8);
    }

    @Test
    public void directByteBufferAddressTest() {
        if (hasUnsafe) {
            assert ByteBufferHelper.directByteBufferAddress(buffer) != 0;
        }
    }

    @Test
    public void wrapDirectByteBufferTest() {
        if (hasUnsafe) {
            int size = 8;
            long address = accessor.allocate(size);
            ByteBuffer buffer = ByteBufferHelper.wrapDirectByteBuffer(address, size);
            assert buffer != null;
            assert buffer.capacity() == size;
            release(buffer);
        }
    }

    @After
    public void after() {
        release(buffer);
    }

    private void release(ByteBuffer buffer) {
        if (hasUnsafe) {
            long address = ByteBufferHelper.directByteBufferAddress(buffer);
            accessor.deallocate(address);
        }
    }

}
