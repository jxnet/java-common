package com.ardikars.common.memory;

import com.ardikars.common.util.Platforms;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class InternalUnsafeOperation {

    public static final boolean BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    public static final boolean UNALIGNED = InternalUnsafeHelper.isUnaligned();

    private static final byte ZERO = 0;
    private static final int BYTE_ARRAY_OFFSET = InternalUnsafeHelper.getUnsafe().arrayBaseOffset(byte[].class);

    /**
     * Limits the number of bytes to copy per Unsafe#copyMemory(long, long, long) to allow safepoint polling
     * during a large copy.
     */
    private static final long UNSAFE_COPY_THRESHOLD = 1024L * 1024L;

    static final long _allocate(int size) {
        return InternalUnsafeHelper.getUnsafe().allocateMemory(size);
    }

    static final long _reallocate(Memory memory, int size) {
        return InternalUnsafeHelper.getUnsafe().reallocateMemory(memory.memoryAddress(), size);
    }

    static final void _deallocate(Memory memory) {
        InternalUnsafeHelper.getUnsafe().freeMemory(memory.memoryAddress());
    }

    static final void _copy(Memory src, int index, Memory dst, int length) {
        // Manual safe-point polling is only needed prior Java9:
        // See https://bugs.openjdk.java.net/browse/JDK-8149596
        if (Platforms.getJavaMojorVersion() <= 8) {
            copyMemoryWithSafePointPolling(src.memoryAddress() + index, dst.memoryAddress(), length);
        } else {
            InternalUnsafeHelper.getUnsafe().copyMemory(src.memoryAddress() + index, dst.memoryAddress(), length);
        }
    }

    private static void copyMemoryWithSafePointPolling(long srcAddr, long dstAddr, long length) {
        while (length > 0) {
            long size = Math.min(length, UNSAFE_COPY_THRESHOLD);
            InternalUnsafeHelper.getUnsafe().copyMemory(srcAddr, dstAddr, size);
            length -= size;
            srcAddr += size;
            dstAddr += size;
        }
    }

    static final void free(long address) {
        InternalUnsafeHelper.getUnsafe().freeMemory(address);
    }

    private static void _ensureHasUnsafe() {
        if (InternalUnsafeHelper.isUnsafeAvailable()) {
            throw new IllegalStateException("unsafe not accessible.");
        }
    }

    static byte _getByte(long address) {
        return InternalUnsafeHelper.getUnsafe().getByte(address);
    }

    static short _getShort(long address) {
        if (UNALIGNED) {
            short v = InternalUnsafeHelper.getUnsafe().getShort(address);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
        }
        return (short) (InternalUnsafeHelper.getUnsafe().getByte(address) << 8 | InternalUnsafeHelper.getUnsafe().getByte(address + 1) & 0xff);
    }

    static short _getShortLE(long address) {
        if (UNALIGNED) {
            short v = InternalUnsafeHelper.getUnsafe().getShort(address);
            return BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
        }
        return (short) (InternalUnsafeHelper.getUnsafe().getByte(address) & 0xff | InternalUnsafeHelper.getUnsafe().getByte(address + 1) << 8);
    }

    static int _getInt(long address) {
        if (UNALIGNED) {
            int v = InternalUnsafeHelper.getUnsafe().getInt(address);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
        }
        return InternalUnsafeHelper.getUnsafe().getByte(address) << 24 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 1) & 0xff) << 16 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 2) & 0xff) <<  8 |
                InternalUnsafeHelper.getUnsafe().getByte(address + 3)  & 0xff;
    }

    static int _getIntLE(long address) {
        if (UNALIGNED) {
            int v = InternalUnsafeHelper.getUnsafe().getInt(address);
            return BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
        }
        return InternalUnsafeHelper.getUnsafe().getByte(address) & 0xff |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 1) & 0xff) <<  8 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 2) & 0xff) << 16 |
                InternalUnsafeHelper.getUnsafe().getByte(address + 3) << 24;
    }

    static long _getLong(long address) {
        if (UNALIGNED) {
            long v = InternalUnsafeHelper.getUnsafe().getLong(address);
            return BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
        }
        return ((long) InternalUnsafeHelper.getUnsafe().getByte(address)) << 56 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 1) & 0xffL) << 48 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 2) & 0xffL) << 40 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 3) & 0xffL) << 32 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 4) & 0xffL) << 24 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 5) & 0xffL) << 16 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 6) & 0xffL) <<  8 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 7)) & 0xffL;
    }

    static long _getLongLE(long address) {
        if (UNALIGNED) {
            long v = InternalUnsafeHelper.getUnsafe().getLong(address);
            return BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
        }
        return (InternalUnsafeHelper.getUnsafe().getByte(address))    & 0xffL        |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 1) & 0xffL) <<  8 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 2) & 0xffL) << 16 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 3) & 0xffL) << 24 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 4) & 0xffL) << 32 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 5) & 0xffL) << 40 |
                (InternalUnsafeHelper.getUnsafe().getByte(address + 6) & 0xffL) << 48 |
                ((long) InternalUnsafeHelper.getUnsafe().getByte(address + 7))  << 56;
    }

    static void _setByte(long address, int value) {
        InternalUnsafeHelper.getUnsafe().putByte(address, (byte) value);
    }

    static void _setShort(long address, int value) {
        if (UNALIGNED) {
            InternalUnsafeHelper.getUnsafe().putShort(
                    address, BIG_ENDIAN_NATIVE_ORDER ? (short) value : Short.reverseBytes((short) value));
        } else {
            InternalUnsafeHelper.getUnsafe().putByte(address, (byte) (value >>> 8));
            InternalUnsafeHelper.getUnsafe().putByte(address + 1, (byte) value);
        }
    }

    static void _setShortLE(long address, int value) {
        if (UNALIGNED) {
            InternalUnsafeHelper.getUnsafe().putShort(
                    address, BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) value) : (short) value);
        } else {
            InternalUnsafeHelper.getUnsafe().putByte(address, (byte) value);
            InternalUnsafeHelper.getUnsafe().putByte(address + 1, (byte) (value >>> 8));
        }
    }

    static void _setInt(long address, int value) {
        if (UNALIGNED) {
            InternalUnsafeHelper.getUnsafe().putInt(address, BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
        } else {
            InternalUnsafeHelper.getUnsafe().putByte(address, (byte) (value >>> 24));
            InternalUnsafeHelper.getUnsafe().putByte(address + 1, (byte) (value >>> 16));
            InternalUnsafeHelper.getUnsafe().putByte(address + 2, (byte) (value >>> 8));
            InternalUnsafeHelper.getUnsafe().putByte(address + 3, (byte) value);
        }
    }

    static void _setIntLE(long address, int value) {
        if (UNALIGNED) {
            InternalUnsafeHelper.getUnsafe().putInt(address, BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
        } else {
            InternalUnsafeHelper.getUnsafe().putByte(address, (byte) value);
            InternalUnsafeHelper.getUnsafe().putByte(address + 1, (byte) (value >>> 8));
            InternalUnsafeHelper.getUnsafe().putByte(address + 2, (byte) (value >>> 16));
            InternalUnsafeHelper.getUnsafe().putByte(address + 3, (byte) (value >>> 24));
        }
    }

    static void _setLong(long address, long value) {
        if (UNALIGNED) {
            InternalUnsafeHelper.getUnsafe().putLong(address, BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
        } else {
            InternalUnsafeHelper.getUnsafe().putByte(address, (byte) (value >>> 56));
            InternalUnsafeHelper.getUnsafe().putByte(address + 1, (byte) (value >>> 48));
            InternalUnsafeHelper.getUnsafe().putByte(address + 2, (byte) (value >>> 40));
            InternalUnsafeHelper.getUnsafe().putByte(address + 3, (byte) (value >>> 32));
            InternalUnsafeHelper.getUnsafe().putByte(address + 4, (byte) (value >>> 24));
            InternalUnsafeHelper.getUnsafe().putByte(address + 5, (byte) (value >>> 16));
            InternalUnsafeHelper.getUnsafe().putByte(address + 6, (byte) (value >>> 8));
            InternalUnsafeHelper.getUnsafe().putByte(address + 7, (byte) value);
        }
    }

    static void _setLongLE(long address, long value) {
        if (UNALIGNED) {
            InternalUnsafeHelper.getUnsafe().putLong(address, BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
        } else {
            InternalUnsafeHelper.getUnsafe().putByte(address, (byte) value);
            InternalUnsafeHelper.getUnsafe().putByte(address + 1, (byte) (value >>> 8));
            InternalUnsafeHelper.getUnsafe().putByte(address + 2, (byte) (value >>> 16));
            InternalUnsafeHelper.getUnsafe().putByte(address + 3, (byte) (value >>> 24));
            InternalUnsafeHelper.getUnsafe().putByte(address + 4, (byte) (value >>> 32));
            InternalUnsafeHelper.getUnsafe().putByte(address + 5, (byte) (value >>> 40));
            InternalUnsafeHelper.getUnsafe().putByte(address + 6, (byte) (value >>> 48));
            InternalUnsafeHelper.getUnsafe().putByte(address + 7, (byte) (value >>> 56));
        }
    }

    static void _setZero(long addr, int length) {
        if (length == 0) {
            return;
        }
        InternalUnsafeHelper.getUnsafe().setMemory(addr, length, ZERO);
    }

    static void _getBytes(AbstractMemory src, int index, Memory dst, int dstIndex, int length) {
        src.checkIndex(index, length);
        if (src.isOutOfBounds(dstIndex, length, dst.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        }
        InternalUnsafeHelper.getUnsafe().copyMemory(src.memoryAddress() + index, dst.memoryAddress() + dstIndex, length);
    }

    static void _getBytes(AbstractMemory src, int index, byte[] dst, int dstIndex, int length) {
        src.checkIndex(index, length);
        if (src.isOutOfBounds(dstIndex, length, dst.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        }
        if (length != 0) {
            InternalUnsafeHelper.getUnsafe().copyMemory(null, src.memoryAddress() + index, dst, (long) (BYTE_ARRAY_OFFSET + dstIndex), length);
        }
    }

    static void _setBytes(AbstractMemory dst, int index, Memory src, int srcIndex, int length) {
        dst.checkIndex(index, length);
        if (dst.isOutOfBounds(srcIndex, length, src.capacity())) {

        }
        InternalUnsafeHelper.getUnsafe().copyMemory(src.memoryAddress() + srcIndex, dst.memoryAddress() + index, length);
    }

    static void _setBytes(AbstractMemory buf, int index, byte[] src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        if (length != 0) {
            InternalUnsafeHelper.getUnsafe().copyMemory(src, (long) (BYTE_ARRAY_OFFSET + srcIndex), null, buf.memoryAddress() + index, length);
        }
    }

    static ByteBuffer _wrap(long memoryAddress, int size) {
        if (InternalUnsafeHelper.getDirectBufferConstructor() != null) {
            try {
                return (ByteBuffer) InternalUnsafeHelper.getDirectBufferConstructor().newInstance(memoryAddress, size);
            } catch (Throwable cause) {
                throw new RuntimeException(cause);
            }
        }
        throw new UnsupportedOperationException(
                "sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

}
