package com.ardikars.common.util;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author common 2018/12/13
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public final class Natives {

    private static final Logger LOGGER = LoggerFactory.getLogger(Natives.class);

    private static final sun.misc.Unsafe UNSAFE;
    private static final boolean UNSAFE_AVAILABLE;

    /**
     * Ensures the {@link Unsafe} object is available.
     * @return returns true is {@link Unsafe} is available. false otherwise.
     */
    public static boolean isUnsafeAvailable() {
        return UNSAFE_AVAILABLE;
    }

    /**
     * Get {@link Unsafe} object.
     * @return returns {@link Unsafe}.
     */
    public static Unsafe getUnsafe() {
        return UNSAFE;
    }

    private static long normalize(long d, int k) {
        return ((long) (Math.ceil(((double) d) / k)) * k);
    }

    /**
     * Find {@link sun.misc.Unsafe} object.
     * @return returns {@link sun.misc.Unsafe} instance.
     */
    private static Object findUnsafe() {
        final Object maybeUnsafe = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                Class<sun.misc.Unsafe> type = sun.misc.Unsafe.class;
                try {
                    final Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                    unsafeField.setAccessible(true);
                    return unsafeField.get(null);
                } catch (Exception e) {
                    for (Field field : type.getDeclaredFields()) {
                        if (type.isAssignableFrom(field.getType())) {
                            field.setAccessible(true);
                            try {
                                return type.cast(field.get(type));
                            } catch (IllegalAccessException e1) {
                                return e1;
                            }
                        }
                    }
                    return e;
                }
            }
        });
        return maybeUnsafe;
    }

    /**
     * Ensure the unsafe supports all necessary methods to work around the mistake in the latest OpenJDK.
     * @param unsafe {@link sun.misc.Unsafe} object.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private static void checkUnsafeInstance(sun.misc.Unsafe unsafe) {
        long arrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
        byte[] buffer = new byte[(int) arrayBaseOffset + (2 * 8)];
        unsafe.putByte(buffer, arrayBaseOffset, (byte) 0x00);
        unsafe.putBoolean(buffer, arrayBaseOffset, false);
        unsafe.putChar(buffer, normalize(arrayBaseOffset, 2), '0');
        unsafe.putShort(buffer, normalize(arrayBaseOffset, 2), (short) 1);
        unsafe.putInt(buffer, normalize(arrayBaseOffset, 4), 2);
        unsafe.putFloat(buffer, normalize(arrayBaseOffset, 4), 3f);
        unsafe.putLong(buffer, normalize(arrayBaseOffset, 8), 4L);
        unsafe.putDouble(buffer, normalize(arrayBaseOffset, 8), 5d);
        unsafe.copyMemory(new byte[buffer.length], arrayBaseOffset, buffer, arrayBaseOffset, buffer.length);
    }

    static {
        Unsafe unsafe = null;
        Object maybeUnsafe = findUnsafe();
        if (maybeUnsafe instanceof Throwable) {
            LOGGER.warn("Unable to get an instance of Natives. Natives-based operations will be unavailable: {}", ((Throwable) maybeUnsafe).getMessage());
        } else {
            unsafe = (sun.misc.Unsafe) maybeUnsafe;
            LOGGER.info("sun.misc.Natives.theUnsafe available.");
            try {
                checkUnsafeInstance(unsafe);
            } catch (Throwable e) {
                LOGGER.warn("Unsafe does not supports all necessary methods: {}", e.getMessage());
                unsafe = null;
            }
        }
        if (unsafe == null) {
            UNSAFE = null;
            UNSAFE_AVAILABLE = false;
        } else {
            UNSAFE = unsafe;
            UNSAFE_AVAILABLE = true;
        }
    }

}
