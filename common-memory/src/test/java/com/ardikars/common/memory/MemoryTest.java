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
public class MemoryTest extends BaseTest {

    private MemoryAllocator memoryAllocator = new DefaultMemoryAllocator();

    private Memory memory;

    @Before
    public void allocate() {
        memory = memoryAllocator.allocate(DEFAULT_CAPACITY, DEFAULT_CAPACITY + INT_SIZE);
        Memory mem = new ReadWriteMemory(memory.memoryAddress(), memory.capacity(), memory.maxCapacity());
        assert mem.memoryAddress() == memory.memoryAddress();
    }

    @Test
    public void capacityAndMaxCapacity() {
        assert memory.capacity() == DEFAULT_CAPACITY;
        assert memory.maxCapacity() == DEFAULT_MAX_CAPACITY;
    }

    @Test
    public void readerAndWriterIndexTest() {
        assert memory.writerIndex() == 0;
        assert memory.readerIndex() == 0;
        memory.writerIndex(BYTE_SIZE);
        assert memory.writerIndex() == BYTE_SIZE;
        memory.readerIndex(BYTE_SIZE);
        assert memory.readerIndex() == BYTE_SIZE;
        memory.setIndex(BYTE_SIZE / BIT_SIZE, BYTE_SIZE / BIT_SIZE);
        assert memory.writerIndex() == BYTE_SIZE / BIT_SIZE;
        assert memory.readerIndex() == BYTE_SIZE / BIT_SIZE;
    }

    @Test
    public void isReadableTest() {
        assert memory.isWritable();
        assert memory.isWritable(DEFAULT_CAPACITY);
        assert !memory.isWritable(DEFAULT_MAX_CAPACITY);
        assert !memory.isReadable();
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            memory.writeByte(i);
        }
        assert !memory.isWritable();
        assert memory.isReadable();
        assert memory.isReadable(DEFAULT_CAPACITY);
        assert !memory.isReadable(DEFAULT_MAX_CAPACITY);
    }

    @Test
    public void readableWriteableAndMaxWriableBytesTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / BIT_SIZE; i++) {
            memory.writeByte(i);
        }
        assert memory.writableBytes() == DEFAULT_CAPACITY / BIT_SIZE;
        assert memory.readableBytes() == DEFAULT_CAPACITY / BIT_SIZE;
        memory.writeInt(1);
        assert memory.writableBytes() == DEFAULT_CAPACITY - ((DEFAULT_CAPACITY / BIT_SIZE) + INT_SIZE);
        assert memory.maxWritableBytes() == DEFAULT_MAX_CAPACITY - ((DEFAULT_CAPACITY / BIT_SIZE) + INT_SIZE);
        memory.readInt();
        assert memory.readableBytes() == DEFAULT_CAPACITY / BIT_SIZE;
    }

    @Test
    public void readerIndexTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        assert memory.readByte() == DUMMY[0];
        memory.markReaderIndex();
        assert memory.readByte() == DUMMY[1];
        memory.resetReaderIndex();
        assert memory.readByte() == DUMMY[1];
    }

    @Test
    public void writerIndexTest() {
        memory.writeByte(1);
        memory.writeByte(2);
        memory.markWriterIndex();
        memory.writeByte(3);
        assert memory.getByte(0) == 1;
        assert memory.getByte(1) == 2;
        assert memory.getByte(2) == 3;
        memory.resetWriterIndex();
        memory.writeByte(1);
        assert memory.getByte(2) == 1;
    }

    @Test
    public void skipBytesTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        for (int i = 0; i < DUMMY.length; i++) {
            if (i % 2 == 0) {
                assert memory.readByte() == i;
            } else {
                memory.skipBytes(1);
            }
        }
    }

    @After
    public void deallocate() {
        memory.release();
    }

}
