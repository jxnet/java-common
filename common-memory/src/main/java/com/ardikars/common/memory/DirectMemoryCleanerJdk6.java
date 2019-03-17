package com.ardikars.common.memory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

final class DirectMemoryCleanerJdk6 implements DirectMemoryCleaner {

    private static final long CLEANER_FIELD_OFFSET;
    private static final Method CLEAN_METHOD;
    private static final Field CLEANER_FIELD;
    private static final boolean CLEANABLE;

    @Override
    public void clean(final ByteBuffer buffer) {
        if (!buffer.isDirect() || !CLEANABLE) {
            return;
        }
        if (System.getSecurityManager() == null) {
            Throwable cause = freeDirectBuffer(buffer);
            if (cause != null) {
                InternalUnsafeHelper.getUnsafe().throwException(cause);
            }
        } else {
            Throwable cause = AccessController.doPrivileged(new PrivilegedAction<Throwable>() {
                @Override
                public Throwable run() {
                    return freeDirectBuffer(buffer);
                }
            });
            if (cause != null) {
                InternalUnsafeHelper.getUnsafe().throwException(cause);
            }
        }
    }


    private Throwable freeDirectBuffer(Buffer buffer) {
        final Object cleaner;
        if (CLEANER_FIELD_OFFSET == -1) {
            try {
                cleaner = CLEANER_FIELD.get(buffer);
            } catch (IllegalAccessException e) {
                return e;
            }
        } else {
            cleaner = InternalUnsafeHelper.getUnsafe().getObject(buffer, CLEANER_FIELD_OFFSET);
        }
        if (cleaner != null) {
            try {
                CLEAN_METHOD.invoke(cleaner);
                return null;
            } catch (IllegalAccessException e) {
                return e;
            } catch (InvocationTargetException e) {
                return e;
            }
        }
        return new NullPointerException("Couldn't get cleaner method.");
    }

    static {
        long fieldOffset;
        Method clean;
        Field cleanerField;
        Throwable error = null;
        final ByteBuffer direct = ByteBuffer.allocateDirect(1);
        try {
            Object maybeCleanerField = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Field cleanerField =  direct.getClass().getDeclaredField("cleaner");
                        if (InternalUnsafeHelper.getUnsafe() != null) {
                            cleanerField.setAccessible(true);
                        }
                        return cleanerField;
                    } catch (Throwable cause) {
                        return cause;
                    }
                }
            });
            if (maybeCleanerField instanceof Throwable) {
                throw (Throwable) maybeCleanerField;
            }

            cleanerField = (Field) maybeCleanerField;

            final Object cleaner;

            if (InternalUnsafeHelper.getUnsafe() != null) {
                fieldOffset = InternalUnsafeHelper.getUnsafe().objectFieldOffset(cleanerField);
                cleaner = InternalUnsafeHelper.getUnsafe().getObject(direct, fieldOffset);
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
            CLEANABLE = true;
        } else {
            CLEANABLE = false;
        }
        CLEANER_FIELD = cleanerField;
        CLEANER_FIELD_OFFSET = fieldOffset;
        CLEAN_METHOD = clean;
    }

}
