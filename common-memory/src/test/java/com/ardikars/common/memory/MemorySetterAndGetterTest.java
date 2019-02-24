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
public class MemorySetterAndGetterTest extends BaseTest {

    private MemoryAllocator memoryAllocator = new DefaultMemoryAllocator();

    private Memory memory;

    @Before
    public void allocate() {
        memory = memoryAllocator.allocate(DEFAULT_CAPACITY, DEFAULT_CAPACITY + INT_SIZE);
    }

    @Test
    public void booleanTest() {
        memory.setBoolean(0, true);
        memory.setBoolean(1, false);
        memory.setBoolean(2, true);
        assert memory.getBoolean(0);
        assert !memory.getBoolean(1);
        assert memory.getBoolean(2);
    }

    @Test
    public void byteTest() {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            memory.setByte(i, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            assert memory.getByte(i) == (byte) i;
        }
    }

    @Test
    public void unsignedByteTest() {
        int value = (Byte.MAX_VALUE * 2) + 1;
        memory.setByte(0, value);
        assert memory.getUnsignedByte(0) == (short) value;
        assert memory.getByte(0) != value;
    }

    @Test
    public void shortTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            memory.setShort(i += SHORT_SIZE, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            assert memory.getShort(i += SHORT_SIZE) == (short) i;
        }
    }

    @Test
    public void shortLETest() {
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            memory.setShortLE(i += SHORT_SIZE, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / SHORT_SIZE; i++) {
            assert memory.getShortLE(i += SHORT_SIZE) == (short) i;
        }
    }

    @Test
    public void unsignedShortTest() {
        int value = (Short.MAX_VALUE * 2) + 1;
        memory.setShort(0, value);
        assert memory.getUnsignedShort(0) == value;
        assert memory.getShort(0) != value;
    }

    @Test
    public void unsignedShortLETest() {
        int value = (Short.MAX_VALUE * 2) + 1;
        memory.setShortLE(0, value);
        assert memory.getUnsignedShortLE(0) == value;
        assert memory.getShortLE(0) != value;
    }

    @Test
    public void intTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.setInt(i += INT_SIZE, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.getInt(i += INT_SIZE) == i;
        }
    }

    @Test
    public void intLETest() {
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.setIntLE(i += INT_SIZE, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.getIntLE(i += INT_SIZE) == i;
        }
    }

    @Test
    public void unsignedIntTest() {
        memory.setInt(0, 0xffffffff);
        assert memory.getUnsignedInt(0) == 4294967295L;
        assert memory.getInt(0) != 4294967295L;
    }

    @Test
    public void unsignedIntLETest() {
        memory.setIntLE(0, 0xffffffff);
        assert memory.getUnsignedIntLE(0) == 4294967295L;
        assert memory.getIntLE(0) != 4294967295L;
    }

    @Test
    public void floatTest() {
        float random = RANDOM.nextFloat();
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.setFloat(i += INT_SIZE, i + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.getFloat(i += INT_SIZE) == i + random;
        }
    }

    @Test
    public void floatLETest() {
        float random = RANDOM.nextFloat();
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            memory.setFloatLE(i += INT_SIZE, i + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / INT_SIZE; i++) {
            assert memory.getFloatLE(i += INT_SIZE) == i + random;
        }
    }

    @Test
    public void longTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.setLong(i += LONG_SIZE, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.getLong(i += LONG_SIZE) == i;
        }
    }

    @Test
    public void longLETest() {
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.setLongLE(i += LONG_SIZE, i);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.getLongLE(i += LONG_SIZE) == i;
        }
    }

    @Test
    public void doubleTest() {
        double random = RANDOM.nextDouble();
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.setDouble(i += LONG_SIZE, i + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.getDouble(i += LONG_SIZE) == i + random;
        }
    }

    @Test
    public void doubleLETest() {
        double random = RANDOM.nextDouble();
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            memory.setDoubleLE(i += LONG_SIZE, i + random);
        }
        for (int i = 0; i < DEFAULT_CAPACITY / LONG_SIZE; i++) {
            assert memory.getDoubleLE(i += LONG_SIZE) == i + random;
        }
    }

    @Test
    public void setBytesTest() {
        memory.setBytes(0, DUMMY);
        for (int i = 0; i < DUMMY.length; i++) {
            assert memory.getByte(i) == DUMMY[i];
        }
        memory.setBytes(1, DUMMY, 1, DUMMY.length - 1);
        for (int i = 1; i < DUMMY.length - 2; i++) {
            assert memory.getByte(i) == DUMMY[i];
        }
        Memory srcMem = memoryAllocator.allocate(DUMMY.length);
        srcMem.setBytes(0, DUMMY);
        memory.setBytes(0, srcMem);
        for (int i = 0; i < DUMMY.length; i++) {
            assert memory.getByte(i) == srcMem.getByte(i);
        }
        srcMem.writerIndex(srcMem.capacity() - 1);
        memory.setBytes(1, srcMem, 2);
        for (int i = 1; i < 2; i++) {
            assert memory.getByte(i) == srcMem.getByte(i - 1);
        }
        memory.setBytes(1, srcMem, 1, DUMMY.length - 1);
        for (int i = 1; i < DUMMY.length - 2; i++) {
            assert memory.getByte(i) == srcMem.getByte(i);
        }
    }

    @Test
    public void getBytesTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        byte[] dst = new byte[DUMMY.length];
        memory.getBytes(0, dst);
        assert dst.length == DUMMY.length;
        for (int i = 0; i < dst.length; i++) {
            assert dst[i] == DUMMY[i];
        }
        dst = new byte[DUMMY.length];
        memory.getBytes(0, dst, 1, dst.length - 1);
        for (int i = 1; i < dst.length - 1; i++) {
            assert dst[i] == DUMMY[i - 1];
        }
        Memory dstMem = memoryAllocator.allocate(DUMMY.length);
        memory.getBytes(0, dstMem);
        for (int i = 0; i < dstMem.capacity(); i++) {
            assert memory.getByte(i) == DUMMY[i];
        }
        dstMem.release();
        dstMem = memoryAllocator.allocate(DUMMY.length);
        memory.getBytes(0, dstMem, DUMMY.length / BIT_SIZE);
        for (int i = 0; i < DUMMY.length / BIT_SIZE; i++) {
            assert memory.getByte(i) == dstMem.getByte(i);
        }
        dstMem.release();
        dstMem = memoryAllocator.allocate(DUMMY.length);
        memory.getBytes(0, dstMem, 1, dstMem.capacity() - 1);
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
