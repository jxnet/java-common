package com.ardikars.common.memory;

import com.ardikars.common.util.Platforms;
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
public class InternalByteBufferHelperTest {

    private DirectMemoryCleaner cleaner = Platforms.getJavaMojorVersion() > 8 ?
            new DirectMemoryCleanerJdk9() : new DirectMemoryCleanerJdk6();

    private boolean hasUnsafe = InternalUnsafeHelper.isUnsafeAvailable();

    private ByteBuffer buffer;

    @Before
    public void before() {
        buffer = ByteBuffer.allocateDirect(8);
    }

    @Test
    public void directByteBufferAddressTest() {
        if (hasUnsafe) {
            assert InternalByteBufferHelper.directByteBufferAddress(buffer) != 0;
        }
    }

    @Test
    public void wrapDirectByteBufferTest() {
        if (hasUnsafe) {
            int size = 8;
            long address = InternalUnsafeOperation._allocate(size);
            ByteBuffer buffer = InternalByteBufferHelper.wrapDirectByteBuffer(address, size);
            assert buffer != null;
            assert buffer.capacity() == size;
            release(buffer);
        }
    }

    @Test
    public void allocateDirectByteBufferTest() {
        if (hasUnsafe) {
            int size = 8;
            ByteBuffer buffer = InternalByteBufferHelper.allocateDirectByteBuffer(size);
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
            long address = InternalByteBufferHelper.directByteBufferAddress(buffer);
            InternalUnsafeOperation.free(address);
        } else {
            cleaner.clean(buffer);
        }
    }

}
