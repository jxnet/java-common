package com.ardikars.common.memory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

final class DirectMemoryCleanerJdk9 implements DirectMemoryCleaner {

    private static final Method INVOKE_CLEANER;
    private static final boolean CLEANABLE;

    @Override
    public void clean(ByteBuffer buffer) {
        if (!buffer.isDirect() || !CLEANABLE) {
            return;
        }
        Throwable cause = freeDirectBuffer(buffer);
        if (cause != null) {
            InternalUnsafeHelper.getUnsafe().throwException(cause);
        }
    }

    private Throwable freeDirectBuffer(final Buffer buffer) {
        Throwable cause = null;
        if (System.getSecurityManager() == null) {
            try {
                INVOKE_CLEANER.invoke(InternalUnsafeHelper.getUnsafe(), buffer);
                cause = null;
            } catch (IllegalAccessException e) {
                cause = e;
            } catch (InvocationTargetException e) {
                cause = e;
            }
        } else {
            cause = AccessController.doPrivileged(new PrivilegedAction<Exception>() {
                @Override
                public Exception run() {
                    try {
                        INVOKE_CLEANER.invoke(InternalUnsafeHelper.getUnsafe(), buffer);
                    } catch (InvocationTargetException e) {
                        return e;
                    } catch (IllegalAccessException e) {
                        return e;
                    }
                    return null;
                }
            });
        }
        return cause;
    }

    static {
        final Method method;
        final Throwable error;
        if (InternalUnsafeHelper.getUnsafe() != null) {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(1);
            Object maybeInvokeMethod = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        // See https://bugs.openjdk.java.net/browse/JDK-8171377
                        Method m = InternalUnsafeHelper.getUnsafe().getClass().getDeclaredMethod(
                                "invokeCleaner", ByteBuffer.class);
                        m.invoke(InternalUnsafeHelper.getUnsafe(), buffer);
                        return m;
                    } catch (NoSuchMethodException e) {
                        return e;
                    } catch (InvocationTargetException e) {
                        return e;
                    } catch (IllegalAccessException e) {
                        return e;
                    }
                }
            });

            if (maybeInvokeMethod instanceof Throwable) {
                method = null;
                error = (Throwable) maybeInvokeMethod;
            } else {
                method = (Method) maybeInvokeMethod;
                error = null;
            }
        } else {
            method = null;
            error = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
        }
        if (error == null) {
            CLEANABLE = true;
        } else {
            CLEANABLE = false;
        }
        INVOKE_CLEANER = method;
    }

}
