package com.ardikars.common.util.buffer;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.util.Callback;
import com.ardikars.common.util.Unsafes;
import sun.misc.Unsafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Buffer clener for jdk9+
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.3
 */
class BufferCleanerJdk9 implements BufferCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferCleanerJdk9.class);

    private static final Method INVOKE_CLEANER;
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
    public void clean(Buffer buffer, Callback<Void> callback) {
        if (System.getSecurityManager() == null) {
            try {
                INVOKE_CLEANER.invoke(UNSAFE, buffer);
            } catch (Throwable cause) {
                callback.onFailure(cause);
            }
        } else {
            Throwable error = AccessController.doPrivileged(new PrivilegedAction<Exception>() {
                @Override
                public Exception run() {
                    try {
                        INVOKE_CLEANER.invoke(UNSAFE, buffer);
                    } catch (InvocationTargetException e) {
                        return e;
                    } catch (IllegalAccessException e) {
                        return e;
                    }
                    return null;
                }
            });
            if (error != null) {
                callback.onFailure(error);
            }
        }
    }

    static {
        Unsafe unsafe = Unsafes.getUnsafe();
        final Method method;
        final Throwable error;
        if (unsafe != null) {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(1);
            Object maybeInvokeMethod = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        // See https://bugs.openjdk.java.net/browse/JDK-8171377
                        Method m = unsafe.getClass().getDeclaredMethod(
                                "invokeCleaner", ByteBuffer.class);
                        m.invoke(unsafe, buffer);
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
            LOGGER.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            LOGGER.debug("java.nio.ByteBuffer.cleaner(): unavailable {}", error);
        }
        INVOKE_CLEANER = method;
        UNSAFE = unsafe;
    }

}
