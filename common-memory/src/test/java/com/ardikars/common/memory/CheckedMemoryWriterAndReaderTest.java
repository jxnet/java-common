package com.ardikars.common.memory;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckedMemoryWriterAndReaderTest extends AbstractMemoryWriterAndReaderTest {

    private final MemoryAllocator MEMORY_ALLOCATOR = new DefaultMemoryAllocator();

    @Override
    protected MemoryAllocator memoryAllocator() {
        return MEMORY_ALLOCATOR;
    }

    @Before
    public void allocate() {
        memory = memoryAllocator().allocate(DEFAULT_CAPACITY, DEFAULT_CAPACITY + INT_SIZE, true);
    }

    @After
    public void deallocate() {
        memory.release();
    }

    @Test
    @Override
    public void booleanTest() {
        doBooleanTest();
    }

    @Test
    @Override
    public void byteTest() {
        doByteTest();
    }

    @Test
    @Override
    public void unsignedByteTest() {
        doUnsignedByteTest();
    }

    @Test
    @Override
    public void shortTest() {
        doShortTest();
    }

    @Test
    @Override
    public void shortLETest() {
        doShortLETest();
    }

    @Test
    @Override
    public void unsignedShortTest() {
        doUnsignedShortTest();
    }

    @Test
    @Override
    public void unsignedShortLETest() {
        doUnsignedShortLETest();
    }

    @Test
    @Override
    public void intTest() {
        doIntTest();
    }

    @Test
    @Override
    public void intLETest() {
        doIntLETest();
    }

    @Test
    @Override
    public void unsignedIntTest() {
        doUnsignedIntTest();
    }

    @Test
    @Override
    public void unsignedIntLETest() {
        doUnsignedIntLETest();
    }

    @Test
    @Override
    public void floatTest() {
        doFloatTest();
    }

    @Test
    @Override
    public void floatLETest() {
        doFloatLETest();
    }

    @Test
    @Override
    public void longTest() {
        doLongTest();
    }

    @Test
    @Override
    public void longLETest() {
        doLongLETest();
    }

    @Test
    @Override
    public void doubleTest() {
        doDoubleTest();
    }

    @Test
    @Override
    public void doubleLETest() {
        doDoubleLETest();
    }

    @Test
    @Override
    public void writeBytesTest() {
        doWriteBytesTest();
    }

    @Test
    @Override
    public void readBytesTest() {
        doReadBytesTest();
    }

    @Test
    @Override
    public void writeReadCharSequaceTest() {
        doWriteReadCharSequaceTest();
    }

}
