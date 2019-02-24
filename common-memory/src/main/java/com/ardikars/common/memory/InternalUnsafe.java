package com.ardikars.common.memory;

import com.ardikars.common.util.Unsafes;

import java.nio.ByteOrder;

class InternalUnsafe {

    public static final boolean BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    public static final boolean UNALIGNED = Unsafes.isUnaligned();

    private static final byte ZERO = 0;
    private static final int BYTE_ARRAY_OFFSET = Unsafes.getUnsafe().arrayBaseOffset(byte[].class);

    static final long _allocate(int size) {
        return Unsafes.getUnsafe().allocateMemory(size);
    }

    static final long _reallocate(Memory memory, int size) {
        return Unsafes.getUnsafe().reallocateMemory(memory.memoryAddress(), size);
    }

    static final void _deallocate(Memory memory) {
        Unsafes.getUnsafe().freeMemory(memory.memoryAddress());
    }

    static final void _copy(Memory src, Memory dst, int length) {
        Unsafes.getUnsafe().copyMemory(src.memoryAddress(), dst.memoryAddress(), length);
    }

    static final void _copy(Memory src, int index, Memory dst, int length) {
        Unsafes.getUnsafe().copyMemory(src.memoryAddress() + index, dst.memoryAddress(), length);
    }

    static final void free(long address) {
        Unsafes.getUnsafe().freeMemory(address);
    }

    private static void _ensureHasUnsafe() {
        if (Unsafes.isUnsafeAvailable()) {
            throw new IllegalStateException("unsafe not accessible.");
        }
    }

    static byte _getByte(long address) {
        return Unsafes.getUnsafe().getByte(address);
    }

    static short _getShort(long address) {
        if (UNALIGNED) {
            short v = Unsafes.getUnsafe().getShort(address);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
        }
        return (short) (Unsafes.getUnsafe().getByte(address) << 8 | Unsafes.getUnsafe().getByte(address + 1) & 0xff);
    }

    static short _getShortLE(long address) {
        if (UNALIGNED) {
            short v = Unsafes.getUnsafe().getShort(address);
            return BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
        }
        return (short) (Unsafes.getUnsafe().getByte(address) & 0xff | Unsafes.getUnsafe().getByte(address + 1) << 8);
    }

    static int _getInt(long address) {
        if (UNALIGNED) {
            int v = Unsafes.getUnsafe().getInt(address);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
        }
        return Unsafes.getUnsafe().getByte(address) << 24 |
                (Unsafes.getUnsafe().getByte(address + 1) & 0xff) << 16 |
                (Unsafes.getUnsafe().getByte(address + 2) & 0xff) <<  8 |
                Unsafes.getUnsafe().getByte(address + 3)  & 0xff;
    }

    static int _getIntLE(long address) {
        if (UNALIGNED) {
            int v = Unsafes.getUnsafe().getInt(address);
            return BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
        }
        return Unsafes.getUnsafe().getByte(address) & 0xff |
                (Unsafes.getUnsafe().getByte(address + 1) & 0xff) <<  8 |
                (Unsafes.getUnsafe().getByte(address + 2) & 0xff) << 16 |
                Unsafes.getUnsafe().getByte(address + 3) << 24;
    }

    static long _getLong(long address) {
        if (UNALIGNED) {
            long v = Unsafes.getUnsafe().getLong(address);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
        }
        return ((long) Unsafes.getUnsafe().getByte(address)) << 56 |
                (Unsafes.getUnsafe().getByte(address + 1) & 0xffL) << 48 |
                (Unsafes.getUnsafe().getByte(address + 2) & 0xffL) << 40 |
                (Unsafes.getUnsafe().getByte(address + 3) & 0xffL) << 32 |
                (Unsafes.getUnsafe().getByte(address + 4) & 0xffL) << 24 |
                (Unsafes.getUnsafe().getByte(address + 5) & 0xffL) << 16 |
                (Unsafes.getUnsafe().getByte(address + 6) & 0xffL) <<  8 |
                (Unsafes.getUnsafe().getByte(address + 7)) & 0xffL;
    }

    static long _getLongLE(long address) {
        if (UNALIGNED) {
            long v = Unsafes.getUnsafe().getLong(address);
            return BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
        }
        return (Unsafes.getUnsafe().getByte(address))    & 0xffL        |
                (Unsafes.getUnsafe().getByte(address + 1) & 0xffL) <<  8 |
                (Unsafes.getUnsafe().getByte(address + 2) & 0xffL) << 16 |
                (Unsafes.getUnsafe().getByte(address + 3) & 0xffL) << 24 |
                (Unsafes.getUnsafe().getByte(address + 4) & 0xffL) << 32 |
                (Unsafes.getUnsafe().getByte(address + 5) & 0xffL) << 40 |
                (Unsafes.getUnsafe().getByte(address + 6) & 0xffL) << 48 |
                ((long) Unsafes.getUnsafe().getByte(address + 7))  << 56;
    }

    static void _setByte(long address, int value) {
        Unsafes.getUnsafe().putByte(address, (byte) value);
    }

    static void _setShort(long address, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putShort(
                    address, BIG_ENDIAN_NATIVE_ORDER ? (short) value : Short.reverseBytes((short) value));
        } else {
            Unsafes.getUnsafe().putByte(address, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(address + 1, (byte) value);
        }
    }

    static void _setShortLE(long address, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putShort(
                    address, BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) value) : (short) value);
        } else {
            Unsafes.getUnsafe().putByte(address, (byte) value);
            Unsafes.getUnsafe().putByte(address + 1, (byte) (value >>> 8));
        }
    }

    static void _setInt(long address, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putInt(address, BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
        } else {
            Unsafes.getUnsafe().putByte(address, (byte) (value >>> 24));
            Unsafes.getUnsafe().putByte(address + 1, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(address + 2, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(address + 3, (byte) value);
        }
    }

    static void _setIntLE(long address, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putInt(address, BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
        } else {
            Unsafes.getUnsafe().putByte(address, (byte) value);
            Unsafes.getUnsafe().putByte(address + 1, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(address + 2, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(address + 3, (byte) (value >>> 24));
        }
    }

    static void _setLong(long address, long value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putLong(address, BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
        } else {
            Unsafes.getUnsafe().putByte(address, (byte) (value >>> 56));
            Unsafes.getUnsafe().putByte(address + 1, (byte) (value >>> 48));
            Unsafes.getUnsafe().putByte(address + 2, (byte) (value >>> 40));
            Unsafes.getUnsafe().putByte(address + 3, (byte) (value >>> 32));
            Unsafes.getUnsafe().putByte(address + 4, (byte) (value >>> 24));
            Unsafes.getUnsafe().putByte(address + 5, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(address + 6, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(address + 7, (byte) value);
        }
    }

    static void _setLongLE(long address, long value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putLong(address, BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
        } else {
            Unsafes.getUnsafe().putByte(address, (byte) value);
            Unsafes.getUnsafe().putByte(address + 1, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(address + 2, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(address + 3, (byte) (value >>> 24));
            Unsafes.getUnsafe().putByte(address + 4, (byte) (value >>> 32));
            Unsafes.getUnsafe().putByte(address + 5, (byte) (value >>> 40));
            Unsafes.getUnsafe().putByte(address + 6, (byte) (value >>> 48));
            Unsafes.getUnsafe().putByte(address + 7, (byte) (value >>> 56));
        }
    }

    static byte _getByte(byte[] array, int index) {
        return Unsafes.getUnsafe().getByte(array, index);
    }

    static short _getShort(byte[] array, int index) {
        if (UNALIGNED) {
            short v = Unsafes.getUnsafe().getShort(array, index);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
        }
        return (short) (Unsafes.getUnsafe().getByte(array, index) << 8 |
                Unsafes.getUnsafe().getByte(array, index + 1) & 0xff);
    }

    static short _getShortLE(byte[] array, int index) {
        if (UNALIGNED) {
            short v = Unsafes.getUnsafe().getShort(array, index);
            return BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
        }
        return (short) (Unsafes.getUnsafe().getByte(array, index) & 0xff |
                Unsafes.getUnsafe().getByte(array, index + 1) << 8);
    }

    static int _getInt(byte[] array, int index) {
        if (UNALIGNED) {
            int v = Unsafes.getUnsafe().getInt(array, index);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
        }
        return Unsafes.getUnsafe().getByte(array, index) << 24 |
                (Unsafes.getUnsafe().getByte(array, index + 1) & 0xff) << 16 |
                (Unsafes.getUnsafe().getByte(array, index + 2) & 0xff) <<  8 |
                Unsafes.getUnsafe().getByte(array, index + 3) & 0xff;
    }

    static int _getIntLE(byte[] array, int index) {
        if (UNALIGNED) {
            int v = Unsafes.getUnsafe().getInt(array, index);
            return BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
        }
        return Unsafes.getUnsafe().getByte(array, index)      & 0xff        |
                (Unsafes.getUnsafe().getByte(array, index + 1) & 0xff) <<  8 |
                (Unsafes.getUnsafe().getByte(array, index + 2) & 0xff) << 16 |
                Unsafes.getUnsafe().getByte(array,  index + 3) << 24;
    }

    static long _getLong(byte[] array, int index) {
        if (UNALIGNED) {
            long v = Unsafes.getUnsafe().getLong(array, index);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
        }
        return ((long) Unsafes.getUnsafe().getByte(array, index)) << 56 |
                (Unsafes.getUnsafe().getByte(array, index + 1) & 0xffL) << 48 |
                (Unsafes.getUnsafe().getByte(array, index + 2) & 0xffL) << 40 |
                (Unsafes.getUnsafe().getByte(array, index + 3) & 0xffL) << 32 |
                (Unsafes.getUnsafe().getByte(array, index + 4) & 0xffL) << 24 |
                (Unsafes.getUnsafe().getByte(array, index + 5) & 0xffL) << 16 |
                (Unsafes.getUnsafe().getByte(array, index + 6) & 0xffL) <<  8 |
                (Unsafes.getUnsafe().getByte(array, index + 7)) & 0xffL;
    }

    static long _getLongLE(byte[] array, int index) {
        if (UNALIGNED) {
            long v = Unsafes.getUnsafe().getLong(array, index);
            return BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
        }
        return Unsafes.getUnsafe().getByte(array, index)      & 0xffL        |
                (Unsafes.getUnsafe().getByte(array, index + 1) & 0xffL) <<  8 |
                (Unsafes.getUnsafe().getByte(array, index + 2) & 0xffL) << 16 |
                (Unsafes.getUnsafe().getByte(array, index + 3) & 0xffL) << 24 |
                (Unsafes.getUnsafe().getByte(array, index + 4) & 0xffL) << 32 |
                (Unsafes.getUnsafe().getByte(array, index + 5) & 0xffL) << 40 |
                (Unsafes.getUnsafe().getByte(array, index + 6) & 0xffL) << 48 |
                ((long) Unsafes.getUnsafe().getByte(array,  index + 7)) << 56;
    }

    static void _setByte(byte[] array, int index, int value) {
        Unsafes.getUnsafe().putByte(array, index, (byte) value);
    }

    static void _setShort(byte[] array, int index, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putShort(array, index,
                    BIG_ENDIAN_NATIVE_ORDER ? (short) value : Short.reverseBytes((short) value));
        } else {
            Unsafes.getUnsafe().putByte(array, index, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(array, index + 1, (byte) value);
        }
    }

    static void _setShortLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putShort(array, index,
                    BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) value) : (short) value);
        } else {
            Unsafes.getUnsafe().putByte(array, index, (byte) value);
            Unsafes.getUnsafe().putByte(array, index + 1, (byte) (value >>> 8));
        }
    }

    static void _setInt(byte[] array, int index, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putInt(array, index, BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
        } else {
            Unsafes.getUnsafe().putByte(array, index, (byte) (value >>> 24));
            Unsafes.getUnsafe().putByte(array, index + 1, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(array, index + 2, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(array, index + 3, (byte) value);
        }
    }

    static void _setIntLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putInt(array, index, BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
        } else {
            Unsafes.getUnsafe().putByte(array, index, (byte) value);
            Unsafes.getUnsafe().putByte(array, index + 1, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(array, index + 2, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(array, index + 3, (byte) (value >>> 24));
        }
    }

    static void _setLong(byte[] array, int index, long value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putLong(array, index, BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
        } else {
            Unsafes.getUnsafe().putByte(array, index, (byte) (value >>> 56));
            Unsafes.getUnsafe().putByte(array, index + 1, (byte) (value >>> 48));
            Unsafes.getUnsafe().putByte(array, index + 2, (byte) (value >>> 40));
            Unsafes.getUnsafe().putByte(array, index + 3, (byte) (value >>> 32));
            Unsafes.getUnsafe().putByte(array, index + 4, (byte) (value >>> 24));
            Unsafes.getUnsafe().putByte(array, index + 5, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(array, index + 6, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(array, index + 7, (byte) value);
        }
    }

    static void _setLongLE(byte[] array, int index, long value) {
        if (UNALIGNED) {
            Unsafes.getUnsafe().putLong(array, index, BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
        } else {
            Unsafes.getUnsafe().putByte(array, index, (byte) value);
            Unsafes.getUnsafe().putByte(array, index + 1, (byte) (value >>> 8));
            Unsafes.getUnsafe().putByte(array, index + 2, (byte) (value >>> 16));
            Unsafes.getUnsafe().putByte(array, index + 3, (byte) (value >>> 24));
            Unsafes.getUnsafe().putByte(array, index + 4, (byte) (value >>> 32));
            Unsafes.getUnsafe().putByte(array, index + 5, (byte) (value >>> 40));
            Unsafes.getUnsafe().putByte(array, index + 6, (byte) (value >>> 48));
            Unsafes.getUnsafe().putByte(array, index + 7, (byte) (value >>> 56));
        }
    }

    static void _setZero(byte[] array, int index, int length) {
        if (length == 0) {
            return;
        }
        Unsafes.getUnsafe().setMemory(array, index, length, ZERO);
    }

    static void _setZero(long addr, int length) {
        if (length == 0) {
            return;
        }
        Unsafes.getUnsafe().setMemory(addr, length, ZERO);
    }

    static void _getBytes(AbstractMemory src, int index, Memory dst, int dstIndex, int length) {
        src.checkIndex(index, length);
        if (src.isOutOfBounds(dstIndex, length, dst.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        }
        Unsafes.getUnsafe().copyMemory(src.memoryAddress() + index, dst.memoryAddress() + dstIndex, length);
    }

    static void _getBytes(AbstractMemory src, int index, byte[] dst, int dstIndex, int length) {
        src.checkIndex(index, length);
        if (src.isOutOfBounds(dstIndex, length, dst.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        }
        if (length != 0) {
            Unsafes.getUnsafe().copyMemory(null, src.memoryAddress() + index, dst, BYTE_ARRAY_OFFSET + dstIndex, length);
        }
    }

    static void _setBytes(AbstractMemory dst, int index, Memory src, int srcIndex, int length) {
        dst.checkIndex(index, length);
        if (dst.isOutOfBounds(srcIndex, length, src.capacity())) {

        }
        Unsafes.getUnsafe().copyMemory(src.memoryAddress() + srcIndex, dst.memoryAddress() + index, length);
    }

    static void _setBytes(AbstractMemory buf, int index, byte[] src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        if (length != 0) {
            Unsafes.getUnsafe().copyMemory(src, BYTE_ARRAY_OFFSET + srcIndex, null, buf.memoryAddress() + index, length);
        }
    }

}
