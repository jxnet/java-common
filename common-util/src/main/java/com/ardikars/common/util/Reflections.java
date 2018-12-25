package com.ardikars.common.util;

import com.ardikars.common.annotation.Helper;

import java.lang.reflect.AccessibleObject;
import java.security.AccessController;
import java.security.PrivilegedAction;

@Helper
public class Reflections {

    /**
     * Try to call {@link AccessibleObject#setAccessible(boolean)} but will catch any {@link SecurityException}
     * and return it.
     * The caller must check if it returns {@code null} and if not handle the returned exception.
     * @param object accessible object.
     * @param checkAccessible accessible.
     * @return returns null on success, throwable otherwise.
     */
    public static Throwable trySetAccessible(final AccessibleObject object, final boolean checkAccessible) {
        Object obj = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    object.setAccessible(checkAccessible);
                    return null;
                } catch (SecurityException e) {
                    return e;
                } catch (RuntimeException e) {
                    return e;
                }
            }
        });
        if (obj == null) {
            return null;
        } else {
            return (Throwable) obj;
        }
    }

}
