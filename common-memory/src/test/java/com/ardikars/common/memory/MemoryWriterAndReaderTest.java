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
public class MemoryWriterAndReaderTest extends BaseTest {

    private MemoryAllocator memoryAllocator = new DefaultMemoryAllocator();

    private Memory memory;

    @Before
    public void allocate() {
        memory = memoryAllocator.allocate(DEFAULT_CAPACITY, DEFAULT_CAPACITY + INT_SIZE);
    }

    @Test
    public void booleanTest() {
        memory.writeBoolean(true);
        memory.writeBoolean(false);
        memory.writeBoolean(true);
        assert memory.readBoolean();
        assert !memory.readBoolean();
        assert memory.readBoolean();
    }

    @Test
    public void byteTest() {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            memory.writeByte(i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            assert memory.readByte() == (byte) i;
        }
    }

    @Test
    public void unsignedByteTest() {
        int value = (Byte.MAX_VALUE * 2) + 1;
        memory.writeByte(value);
        assert memory.readUnsignedByte() == (short) value;
        memory.readerIndex(0);
        assert memory.readByte() != value;
    }

    @Test
    public void shortTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            memory.writeShort(i + SHORT_SIZE);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            assert memory.readShort() == (short) (i + SHORT_SIZE);
        }
    }

    @Test
    public void shortLETest() {
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            memory.writeShortLE(i + SHORT_SIZE);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            assert memory.readShortLE() == (short) (i + SHORT_SIZE);
        }
    }

    @Test
    public void unsignedShortTest() {
        int value = (Short.MAX_VALUE * 2) + 1;
        memory.writeShort(value);
        assert memory.readUnsignedShort() == value;
        memory.readerIndex(0);
        assert memory.readShort() != value;
    }

    @Test
    public void unsignedShortLETest() {
        int value = (Short.MAX_VALUE * 2) + 1;
        memory.writeShortLE(value);
        assert memory.readUnsignedShortLE() == value;
        memory.readerIndex(0);
        assert memory.readShortLE() != value;
    }

    @Test
    public void intTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.writeInt(i + INT_SIZE);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.readInt() == (i + INT_SIZE);
        }
    }

    @Test
    public void intLETest() {
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.writeIntLE(i + INT_SIZE);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.readIntLE() == (i + INT_SIZE);
        }
    }

    @Test
    public void unsignedIntTest() {
        memory.writeInt(0xffffffff);
        assert memory.readUnsignedInt() == 4294967295L;
        memory.readerIndex(0);
        assert memory.readInt() != 4294967295L;
    }

    @Test
    public void unsignedIntLETest() {
        memory.writeIntLE(0xffffffff);
        assert memory.readUnsignedIntLE() == 4294967295L;
        memory.readerIndex(0);
        assert memory.readIntLE() != 4294967295L;
    }

    @Test
    public void floatTest() {
        float random = RANDOM.nextFloat();
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.writeFloat(i + INT_SIZE + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.readFloat() == (i + INT_SIZE + random);
        }
    }

    @Test
    public void floatLETest() {
        float random = RANDOM.nextFloat();
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.writeFloatLE(i + INT_SIZE + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.readFloatLE() == (i + INT_SIZE + random);
        }
    }

    @Test
    public void longTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.writeLong(i + LONG_SIZE);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.readLong() == (long) (i + LONG_SIZE);
        }
    }

    @Test
    public void longLETest() {
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.writeLongLE(i + LONG_SIZE);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.readLongLE() == (long) (i + LONG_SIZE);
        }
    }

    @Test
    public void doubleTest() {
        double random = RANDOM.nextDouble();
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.writeDouble(i + LONG_SIZE + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.readDouble() == (i + LONG_SIZE + random);
        }
    }

    @Test
    public void doubleLETest() {
        double random = RANDOM.nextDouble();
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.writeDoubleLE(i + LONG_SIZE + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.readDoubleLE() == (i + LONG_SIZE + random);
        }
    }

    @Test
    public void writeBytesTest() {
        memory.writeBytes(DUMMY);
        for (byte val : DUMMY) {
            assert memory.readByte() == val;
        }
        memory.readerIndex(0);
        memory.writerIndex(0);
        memory.writeBytes(DUMMY, 1, DUMMY.length - 1);
        for (int i = 0; i < DUMMY.length - 1; i++) {
            assert memory.getByte(i) == DUMMY[i + 1];
        }
        Memory memSrc = memoryAllocator.allocate(DUMMY.length);
        assert memSrc.capacity() == DUMMY.length;
        memSrc.writeBytes(DUMMY);
        memory.writerIndex(0);
        memory.writeBytes(memSrc);
        for (int i = 0; i < memSrc.capacity(); i++) {
            assert memory.getByte(i) == DUMMY[i];
        }
        memSrc.readerIndex(0);
        memSrc.writerIndex(5);
        memory.writeBytes(memSrc, 2);
        for (int i = 0; i < 2; i++) {
            assert memory.getByte(i) == DUMMY[i];
        }
        memory.readerIndex(0);
        memory.writerIndex(0);
        memSrc.setByte(2, 2);
        memSrc.setByte(3, 3);
        memory.writeBytes(memSrc, 2, 2);
        for (int i = 0; i < 2; i++) {
            assert memory.getByte(i) == DUMMY[i + 2];
        }
        memSrc.release();
    }

    @Test
    public void readBytesTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        byte[] dst = new byte[DUMMY.length];
        memory.readBytes(dst);
        assert dst.length == DUMMY.length;
        for (int i = 0; i < dst.length; i++) {
            assert dst[i] == DUMMY[i];
        }
        dst = new byte[DUMMY.length];
        memory.readerIndex(0);
        memory.readBytes(dst, 1, dst.length - 1);
        for (int i = 1; i < dst.length - 1; i++) {
            assert dst[i] == DUMMY[i - 1];
        }
        memory.readerIndex(0);
        Memory dstMem = memoryAllocator.allocate(DUMMY.length);
        memory.readBytes(dstMem);
        for (int i = 0; i < dstMem.capacity(); i++) {
            assert memory.getByte(i) == DUMMY[i];
        }
        dstMem.release();
        memory.readerIndex(0);
        dstMem = memoryAllocator.allocate(DUMMY.length);
        memory.readBytes(dstMem, DUMMY.length / BIT_SIZE);
        for (int i = 0; i < DUMMY.length / BIT_SIZE; i++) {
            assert memory.getByte(i) == dstMem.getByte(i);
        }
        dstMem.release();
        memory.readerIndex(0);
        dstMem = memoryAllocator.allocate(DUMMY.length);
        memory.readBytes(dstMem, 1, dstMem.capacity() - 1);
        for (int i = 1; i < dstMem.capacity() - 1; i++) {
            assert dstMem.getByte(i) == DUMMY[i - 1];
        }
        dstMem.release();
    }

    @After
    public void deallocate() {
        memory.release();
    }

}
