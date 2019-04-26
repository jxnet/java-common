package com.ardikars.common.memory.accessor;

import com.ardikars.common.memory.internal.UnsafeHelper;

import java.nio.ByteOrder;

public class MemoryAccessors {

    private static final boolean UNALIGN = UnsafeHelper.isUnaligned();

    public static final boolean BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

    public static MemoryAccessor memoryAccessor() {
        return memoryAccessor(UNALIGN, BIG_ENDIAN_NATIVE_ORDER);
    }

    public static MemoryAccessor memoryAccessor(boolean unaligned, boolean bigEndianess) {
        MemoryAccessor memoryAccessor;
        if (unaligned) {
            if (bigEndianess) {
                memoryAccessor = new UnalignBEMemoryAccessor();
            } else {
                memoryAccessor = new UnalignLEMemoryAccessor();
            }
        } else {
            memoryAccessor = new AlignMemoryAcessor();
        }
        return memoryAccessor;
    }

}
