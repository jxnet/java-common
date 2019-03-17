package com.ardikars.common.memory;


import com.ardikars.common.util.Platforms;
import com.ardikars.common.util.Properties;
import com.ardikars.common.util.Reflections;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class InternalUnsafeHelper {

    private static final UnsupportedOperationException UNSUPPORTED_OPERATION_EXCEPTION
            = new UnsupportedOperationException("sun.misc.Unsafe unavailable.");

    private static final Unsafe UNSAFE;
    private static final boolean UNSAFE_AVAILABLE;
    private static final boolean UNALIGNED;
    private static final List<Throwable> NO_UNSAFE_CAUSES;
    private static long BUFFER_ADDRESS_FIELD_OFFSET = -1;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;

    /**
     * Ensures the {@link Unsafe} object is available.
     * @return returns true is {@link Unsafe} is available. false otherwise.
     */
    public static boolean isUnsafeAvailable() {
        return UNSAFE_AVAILABLE;
    }

    /**
     * Returns aligned.
     * @return returns {@code true} if and only if the platform supports unaligned access.
     */
    public static boolean isUnaligned() {
        return UNALIGNED;
    }

    /**
     * Get {@link Unsafe} object.
     * @return returns {@link Unsafe}.
     */
    public static Unsafe getUnsafe() {
        if (!isUnsafeAvailable()) {
            throw UNSUPPORTED_OPERATION_EXCEPTION;
        }
        return UNSAFE;
    }

    /**
     * Get direct {@link ByteBuffer} constructor.
     * @return returns direct {@link ByteBuffer} constructor.
     */
    public static Constructor<?> getDirectBufferConstructor() {
        return DIRECT_BUFFER_CONSTRUCTOR;
    }

    /**
     * Returns immutable no unsafe causes.
     * @return returns immutable no unsafe causes.
     */
    public List<Throwable> getNoUnsafeCauses() {
        return NO_UNSAFE_CAUSES;
    }

    private static long normalize(long d, int k) {
        return ((long) (Math.ceil(((double) d) / k)) * k);
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
    }

    private static ClassLoader getSystemClassLoader() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return ClassLoader.getSystemClassLoader();
                }
            });
        }
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
                    Throwable unsafeFieldSetAccessible = Reflections.trySetAccessible(unsafeField, true);
                    if (unsafeFieldSetAccessible != null) {
                        return unsafeFieldSetAccessible;
                    }
                    return unsafeField.get(null);
                } catch (Exception e) {
                    for (Field field : type.getDeclaredFields()) {
                        if (type.isAssignableFrom(field.getType())) {
                            Throwable fieldSetAccessible = Reflections.trySetAccessible(field, true);
                            if (fieldSetAccessible != null) {
                                return fieldSetAccessible;
                            }
                            try {
                                return type.cast(field.get(type));
                            } catch (IllegalAccessException e1) {
                                try {
                                    Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor();
                                    Reflections.trySetAccessible(field, true);
                                    return c.newInstance();
                                } catch (Exception ex) {
                                    return ex;
                                }
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
     * Java9 has jdk.internal.misc.Unsafe and not all methods are propagated to sun.misc.Unsafe.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private static Object checkJdk9Unsafe() {
        Object maybeException = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Class<?> internalUnsafeClass = getClassLoader(InternalUnsafeHelper.class)
                            .loadClass("jdk.internal.misc.Unsafe");
                    Method method = internalUnsafeClass.getDeclaredMethod("getUnsafe");
                    // in java 9+ Unsafe.getUnsafe is not accessible
                    Reflections.trySetAccessible(method, true);
                    return method.invoke(null);
                } catch (Throwable e) {
                    return e;
                }
            }
        });
        return maybeException;
    }

    /**
     * Ensure the unsafe supports all necessary methods to work around the mistake in the latest OpenJDK.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private static Object checkJdk6Unsafe(sun.misc.Unsafe unsafe) {
        try {
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
            return true;
        } catch (Throwable e) {
            return e;
        }
    }

    private static Object checkUnaligned(final Unsafe unsafe) {
        Object maybeUnaligned = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Class<?> bitsClass =
                            Class.forName("java.nio.Bits", false, getSystemClassLoader());
                    int version = Platforms.getJavaMojorVersion();
                    if (version >= 9) {
                        // Java9/10 use all lowercase and later versions all uppercase.
                        String fieldName = version >= 11 ? "UNALIGNED" : "unaligned";
                        try {
                            Field unalignedField = bitsClass.getDeclaredField(fieldName);
                            if (unalignedField.getType() == boolean.class) {
                                long offset = unsafe.staticFieldOffset(unalignedField);
                                Object object = unsafe.staticFieldBase(unalignedField);
                                return unsafe.getBoolean(object, offset);
                            }
                        } catch (NoSuchFieldException ignore) {
                        }
                    }
                    Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned");
                    Throwable cause = Reflections.trySetAccessible(unalignedMethod, true);
                    if (cause != null) {
                        return cause;
                    }
                    return unalignedMethod.invoke(null);
                } catch (NoSuchMethodException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                } catch (IllegalAccessException e) {
                    return e;
                } catch (ClassNotFoundException e) {
                    return e;
                } catch (InvocationTargetException e) {
                    return e;
                }
            }
        });
        return maybeUnaligned;
    }

    private static Object findBufferAddressField(final ByteBuffer direct, final sun.misc.Unsafe unsafe) {
        final sun.misc.Unsafe finalUnsafe = unsafe;
        final Object maybeAddressField = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    final Field field = Buffer.class.getDeclaredField("address");
                    final long offset = finalUnsafe.objectFieldOffset(field);
                    final long address = finalUnsafe.getLong(direct, offset);
                    if (address == 0) {
                        // if it's buffer is direct buffer, address will no not 0.
                        return new IllegalStateException("Non direct buffer.");
                    }
                    return field;
                } catch (NoSuchFieldException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                }
            }
        });
        return maybeAddressField;
    }

    private static Object findDirectBufferConstructor(final ByteBuffer direct, final sun.misc.Unsafe unsafe) {
        final Object maybeDirectBufferConstructor = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    final Constructor<?> constructor =
                            direct.getClass().getDeclaredConstructor(long.class, int.class);
                    Throwable cause = Reflections.trySetAccessible(constructor, true);
                    if (cause != null) {
                        return cause;
                    }
                    return constructor;
                } catch (NoSuchMethodException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                }
            }
        });
        return maybeDirectBufferConstructor;
    }

    static {
        Unsafe unsafe = null;
        Constructor<?> directBufferConstructor = null;
        List<Throwable> causes = new ArrayList<Throwable>();
        Object maybeUnsafe = findUnsafe();
        final boolean unaligned;
        if (maybeUnsafe instanceof Throwable) {
            //LOGGER.warn("Unable to get an instance of Unsafes. Unsafes-based operations will be unavailable: {}", ((Throwable) maybeUnsafe).getMessage());
            unaligned = false;
            causes.add((Throwable) maybeUnsafe);
        } else {

            unsafe = (sun.misc.Unsafe) maybeUnsafe;
            //LOGGER.info("sun.misc.Unsafes.theUnsafe available.");

            Object maybeExceptionJdk6 = checkJdk6Unsafe(unsafe);
            if (maybeExceptionJdk6 instanceof Throwable) {
                //LOGGER.warn("Unsafe does not supports all necessary methods: {}", ((Throwable) maybeExceptionJdk6).getMessage());
                unsafe = null;
                causes.add((Throwable) maybeExceptionJdk6);
            }

            Object maybeUnaligned = checkUnaligned(unsafe);
            if (maybeUnaligned instanceof Boolean) {
                unaligned = (Boolean) maybeUnaligned;
                //LOGGER.debug("java.nio.Bits.unaligned: available, {}", unaligned);
            } else {
                String arch = Properties.getProperty("os.arch", "");
                unaligned = arch.matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
//                Throwable t = (Throwable) maybeUnaligned;
                //LOGGER.debug("java.nio.Bits.unaligned: unavailable {}", unaligned, t);
            }

            if (Platforms.getJavaMojorVersion() >= 9) {
                Object maybeExceptionJdk9 = checkJdk9Unsafe();
                if (maybeExceptionJdk9 instanceof Throwable) {
                    //LOGGER.warn("Unsafe does not supports all necessary methods: {}", ((Throwable) maybeExceptionJdk9).getMessage());
                    causes.add((Throwable) maybeExceptionJdk9);
                }
            }

            ByteBuffer direct = ByteBuffer.allocateDirect(1);
            Object maybeBufferAddressField = findBufferAddressField(direct, unsafe);
            if (maybeBufferAddressField instanceof Field) {
//                    LOGGER.debug("java.nio.Buffer.address: available");
                BUFFER_ADDRESS_FIELD_OFFSET = unsafe.objectFieldOffset((Field) maybeBufferAddressField);
            } else {
//                    LOGGER.warn("java.nio.Buffer.address: unavailable: {}", ((Throwable) maybeBufferAddressField).getMessage());
                unsafe = null;
            }

            long address = -1;
            try {
                Object maybeDirectBufferConstructor = findDirectBufferConstructor(direct, unsafe);
                if (maybeDirectBufferConstructor instanceof Constructor<?>) {
                    address = unsafe.allocateMemory(1);
                    // try to use the constructor now
                    try {
                        ((Constructor<?>) maybeDirectBufferConstructor).newInstance(address, 1);
                        directBufferConstructor = (Constructor<?>) maybeDirectBufferConstructor;
//                            LOGGER.debug("direct buffer constructor: available");
                    } catch (InstantiationException e) {
                        directBufferConstructor = null;
                    } catch (IllegalAccessException e) {
                        directBufferConstructor = null;
                    } catch (InvocationTargetException e) {
                        directBufferConstructor = null;
                    }
                } else {
//                        LOGGER.debug(
//                                "direct buffer constructor: unavailable",
//                                (Throwable) maybeDirectBufferConstructor);
                    directBufferConstructor = null;
                }
            } finally {
                if (address != -1) {
                    unsafe.freeMemory(address);
                }
            }
        }
        if (unsafe == null) {
            UNSAFE = null;
            UNSAFE_AVAILABLE = false;
        } else {
            UNSAFE = unsafe;
            UNSAFE_AVAILABLE = true;
        }
        UNALIGNED = unaligned;
        NO_UNSAFE_CAUSES = Collections.unmodifiableList(causes);
        DIRECT_BUFFER_CONSTRUCTOR = directBufferConstructor;
    }

}
