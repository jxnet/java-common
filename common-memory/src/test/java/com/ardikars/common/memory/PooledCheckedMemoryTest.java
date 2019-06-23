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
public class PooledCheckedMemoryTest extends AbstractMemoryTest {

    private final MemoryAllocator MEMORY_ALLOCATOR = Memories.allocator(2, 5, DEFAULT_CAPACITY + INT_SIZE);

    public PooledCheckedMemoryTest() {
        super(true);
    }

    @Override
    protected MemoryAllocator memoryAllocator() {
        return MEMORY_ALLOCATOR;
    }

    @Before
    public void allocate() {
        memory = memoryAllocator().allocate(DEFAULT_CAPACITY, DEFAULT_CAPACITY + INT_SIZE, true);
        Memory mem = new CheckedMemory(memory.memoryAddress(), memory.capacity(), memory.maxCapacity());
        assert mem.memoryAddress() == memory.memoryAddress();
    }

    @After
    public void deallocate() {
        memory.release();
        MEMORY_ALLOCATOR.close();
    }

    @Test
    @Override
    public void capacityAndMaxCapacityTest() {
        doCapacityAndMaxCapacityTest();
    }

    @Test
    @Override
    public void readerAndWriterIndexTest() {
        doReaderAndWriterIndexTest();
    }

    @Test
    @Override
    public void isReadableTest() {
        doIsReadableTest();
    }

    @Test
    @Override
    public void readableWriteableAndMaxWriableBytesTest() {
        doReadableWriteableAndMaxWriableBytesTest();
    }

    @Test
    @Override
    public void readerIndexTest() {
        doReaderIndexTest();
    }

    @Test
    @Override
    public void writerIndexTest() {
        doWriterIndexTest();
    }

    @Test
    @Override
    public void skipBytesTest() {
        doSkipBytesTest();
    }

    @Test
    @Override
    public void sliceTest() {
        doSliceTest();
    }

    @Test
    @Override
    public void copyTest() {
        doCopyTest();
    }

    @Test
    @Override
    public void clearTest() {
        doClearTest();
    }

    @Test
    @Override
    public void newCapacityTest() {
        doNewCapacityTest();
    }

    @Test
    @Override
    public void duplicateTest() {
        doDuplicateTest();
    }

    @Test
    @Override
    public void nioBufferTest() {
        doNioBufferTest();
    }

}
