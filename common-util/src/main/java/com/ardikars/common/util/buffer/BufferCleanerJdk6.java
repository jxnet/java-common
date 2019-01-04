package com.ardikars.common.util.buffer;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.util.Callback;
import com.ardikars.common.util.Unsafes;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Buffer cleaner for jdk6+
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.3
 */
class BufferCleanerJdk6 implements BufferCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferCleanerJdk6.class);

    private static final long CLEANER_FIELD_OFFSET;
    private static final Method CLEAN_METHOD;
    private static final Field CLEANER_FIELD;
    private static final Unsafe UNSAFE;

    @Override
    public void clean(Buffer buffer) {
        clean(buffer, new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                // do nothing
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.warn(throwable);
            }
        });
    }

    @Override
    public void clean(final Buffer buffer, final Callback<Void> callback) {
        if (!buffer.isDirect()) {
            callback.onFailure(new IllegalArgumentException("Given buffer is not a direct buffer."));
            return;
        }
        if (System.getSecurityManager() == null) {
            try {
                freeDirectBuffer(buffer, callback);
            } catch (Throwable cause) {
                callback.onFailure(cause);
            }
        } else {
            Throwable cause = AccessController.doPrivileged(new PrivilegedAction<Throwable>() {
                @Override
                public Throwable run() {
                    try {
                        freeDirectBuffer(buffer, callback);
                        return null;
                    } catch (Throwable cause) {
                        return cause;
                    }
                }
            });
            if (cause != null) {
                callback.onFailure(cause);
            }
        }
    }

    private void freeDirectBuffer(Buffer buffer, Callback<Void> callback) {
        final Object cleaner;
        if (CLEANER_FIELD_OFFSET == -1) {
            try {
                cleaner = CLEANER_FIELD.get(buffer);
            } catch (IllegalAccessException e) {
                callback.onFailure(e);
                return;
            }
        } else {
            cleaner = UNSAFE.getObject(buffer, CLEANER_FIELD_OFFSET);
        }
        if (cleaner != null) {
            try {
                CLEAN_METHOD.invoke(cleaner);
                callback.onSuccess(null);
            } catch (IllegalAccessException e) {
                callback.onFailure(e);
            } catch (InvocationTargetException e) {
                callback.onFailure(e);
            }
        }
    }

    static {
        final Unsafe unsafe = Unsafes.getUnsafe();
        long fieldOffset;
        Method clean;
        Field cleanerField;
        Throwable error = null;
        final ByteBuffer direct = ByteBuffer.allocateDirect(1);
        try {
            Object mayBeCleanerField = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Field cleanerField =  direct.getClass().getDeclaredField("cleaner");
                        if (unsafe != null) {
                            cleanerField.setAccessible(true);
                        }
                        return cleanerField;
                    } catch (Throwable cause) {
                        return cause;
                    }
                }
            });
            if (mayBeCleanerField instanceof Throwable) {
                throw (Throwable) mayBeCleanerField;
            }

            cleanerField = (Field) mayBeCleanerField;

            final Object cleaner;

            if (unsafe != null) {
                fieldOffset = unsafe.objectFieldOffset(cleanerField);
                cleaner = unsafe.getObject(direct, fieldOffset);
            } else {
                fieldOffset = -1;
                cleaner = cleanerField.get(direct);
            }
            clean = cleaner.getClass().getDeclaredMethod("clean");
            clean.invoke(cleaner);
        } catch (Throwable t) {
            // We don't have ByteBuffer.cleaner().
            fieldOffset = -1;
            clean = null;
            error = t;
            cleanerField = null;
        }

        if (error == null) {
            LOGGER.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            LOGGER.debug("java.nio.ByteBuffer.cleaner(): unavailable {}", error);
        }
        CLEANER_FIELD = cleanerField;
        CLEANER_FIELD_OFFSET = fieldOffset;
        CLEAN_METHOD = clean;
        UNSAFE = unsafe;
    }

}
