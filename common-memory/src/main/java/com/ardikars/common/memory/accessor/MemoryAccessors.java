package com.ardikars.common.memory.accessor;

import com.ardikars.common.memory.internal.UnsafeHelper;

import java.nio.ByteOrder;

/**
 * Used to get {@link MemoryAccessor} instance.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public class MemoryAccessors {

    private static final boolean UNALIGN = UnsafeHelper.isUnaligned();

    public static final boolean BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

    /**
     * Get default {@link MemoryAccessor} instance.
     * @return returns default {@link MemoryAccessor} instance.
     */
    public static MemoryAccessor memoryAccessor() {
        return memoryAccessor(UNALIGN, BIG_ENDIAN_NATIVE_ORDER);
    }

    /**
     * Get {@link MemoryAccessor} instance with given aligness and endianess.
     * @param unaligned unaligned memory.
     * @param bigEndianess endianess.
     * @return returns {@link MemoryAccessor} instance.
     */
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
