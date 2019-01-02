/**
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ardikars.common.util.buffer;

import com.ardikars.common.annotation.Helper;
import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.util.Callback;
import com.ardikars.common.util.Reflections;
import com.ardikars.common.util.Unsafes;
import com.ardikars.common.util.Platforms;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * ByteBuffer utils.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.3
 */
@Helper
public final class ByteBuffers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteBuffers.class);

    private static final Unsafe UNSAFE;
    private static final BufferCleaner CLEANER;
    private static long BUFFER_ADDRESS_FIELD_OFFSET = -1;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;

    /**
     * Returns management address of direct buffer.
     * @param buffer direct buffer.
     * @return returns management address.
     * @since 1.2.3
     */
    public static long directByteBufferAddress(ByteBuffer buffer) {
        return UNSAFE.getLong(buffer, BUFFER_ADDRESS_FIELD_OFFSET);
    }

    /**
     * Free direct buffer.
     * @param buffer buffer.
     */
    public static void freeDirectByteBuffer(ByteBuffer buffer) {
        CLEANER.clean(buffer);
    }

    /**
     * Free direct buffer.
     * @param memoryAddress management address.
     */
    public static void freeDirectBuffer(long memoryAddress) {
        UNSAFE.freeMemory(memoryAddress);
    }

    /**
     * Free direct buffer.
     * @param buffer buffer.
     * @param callback callback.
     */
    public static void freeDirectByteBuffer(ByteBuffer buffer, Callback<Void> callback) {
        CLEANER.clean(buffer, callback);
    }

    /**
     * Wrap direct buffer from management address with no {@link BufferCleaner}.
     * @param memoryAddress management address.
     * @param size buffer size.
     * @return returns {@link ByteBuffer} object.
     */
    public static ByteBuffer wrapDirectByteBuffer(long memoryAddress, int size) {
        if (DIRECT_BUFFER_CONSTRUCTOR != null) {
            try {
                return (ByteBuffer) DIRECT_BUFFER_CONSTRUCTOR.newInstance(memoryAddress, size);
            } catch (Throwable cause) {
                throw new RuntimeException(cause);
            }
        }
        throw new UnsupportedOperationException(
                "sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

    /**
     * Allocate direct buffer with no {@link BufferCleaner} and no zeroing buffer.
     * @param size buffer size.
     * @return returns {@link ByteBuffer} object.
     */
    public static ByteBuffer allocateDirectByteBuffer(int size) {
        if (DIRECT_BUFFER_CONSTRUCTOR != null) {
            long address = UNSAFE.allocateMemory(size);
            return wrapDirectByteBuffer(address, size);
        }
        throw new UnsupportedOperationException(
                "sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

    /**
     * Find buffer address field.
     * @param unsafe {@link sun.misc.Unsafe} object.
     * @return returns buffer address field, or returns exception on failure.
     */
    private static Object findBufferAddressField(ByteBuffer direct, sun.misc.Unsafe unsafe) {
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

    private static Object findDirectBufferConstructor(ByteBuffer direct, sun.misc.Unsafe unsafe) {
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
        Unsafe unsafe = Unsafes.getUnsafe();
        Constructor<?> directBufferConstructor;
        if (unsafe != null) {
            ByteBuffer direct = ByteBuffer.allocateDirect(1);
            Object maybeBufferAddressField = findBufferAddressField(direct, unsafe);
            if (maybeBufferAddressField instanceof Field) {
                LOGGER.debug("java.nio.Buffer.address: available");
                BUFFER_ADDRESS_FIELD_OFFSET = unsafe.objectFieldOffset((Field) maybeBufferAddressField);
            } else {
                LOGGER.warn("java.nio.Buffer.address: unavailable: {}", ((Throwable) maybeBufferAddressField).getMessage());
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
                        LOGGER.debug("direct buffer constructor: available");
                    } catch (InstantiationException e) {
                        directBufferConstructor = null;
                    } catch (IllegalAccessException e) {
                        directBufferConstructor = null;
                    } catch (InvocationTargetException e) {
                        directBufferConstructor = null;
                    }
                } else {
                    LOGGER.debug(
                            "direct buffer constructor: unavailable",
                            (Throwable) maybeDirectBufferConstructor);
                    directBufferConstructor = null;
                }
            } finally {
                if (address != -1) {
                    unsafe.freeMemory(address);
                }
            }
        } else {
            directBufferConstructor = null;
            LOGGER.debug("Unsafe is unavailable, so direct buffer constructor will be null.");
        }

        if (Platforms.getJavaMojorVersion() < 9) {
            CLEANER = new BufferCleanerJdk6();
        } else {
            CLEANER = new BufferCleanerJdk9();
        }
        DIRECT_BUFFER_CONSTRUCTOR = directBufferConstructor;
        UNSAFE = unsafe;
    }

}
