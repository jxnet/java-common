//package com.ardikars.common.memory;
//
//import java.security.AccessController;
//import java.security.PrivilegedAction;
//
//final class InternalPropertyHelper {
//
//    /**
//     * Returns the value of the Java system property with the specified
//     * {@code key}, while falling back to the specified default value if
//     * the property access fails.
//     *
//     * @param key key.
//     * @return returns the property value. null if there's no such property or if an access to the
//     *         specified property is not allowed.
//     * @since 1.2.3
//     */
//    public static String getProperty(final String key) {
//        return getProperty(key, null);
//    }
//
//    /**
//     * Returns the value of the Java system property with the specified
//     * {@code key}, while falling back to the specified default value if
//     * the property access fails.
//     *
//     * @param key key.
//     * @param defaultValue default value.
//     * @return the property value.
//     *         {@code defaultValue} if there's no such property or if an access to the
//     *         specified property is not allowed.
//     * @since 1.2.3
//     */
//    public static String getProperty(final String key, String defaultValue) {
//        if (key == null || key.isEmpty()) {
//            throw new IllegalArgumentException("Property key should be not null");
//        }
//        String value = null;
//        try {
//            if (System.getSecurityManager() == null) {
//                value = System.getProperty(key);
//            } else {
//                value = AccessController.doPrivileged(new PrivilegedAction<String>() {
//                    @Override
//                    public String run() {
//                        return System.getProperty(key);
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            return defaultValue;
//        }
//        if (value != null) {
//            return value;
//        }
//        return defaultValue;
//    }
//
//    /**
//     * Returns the value of the Java system property with the specified
//     * {@code key}, while falling back to the specified default value if
//     * the property access fails.
//     *
//     * @param key key.
//     * @param defaultValue default value.
//     * @return the property value.
//     *         {@code defaultValue} if there's no such property or if an access to the
//     *         specified property is not allowed.
//     * @since 1.2.6
//     */
//    public static boolean getBoolean(final String key, boolean defaultValue) {
//        String value = getProperty(key);
//        if (value == null) {
//            return defaultValue;
//        }
//        value = value.trim().toLowerCase();
//        if (value.isEmpty()) {
//            return defaultValue;
//        }
//
//        if ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || "1".equals(value)) {
//            return true;
//        }
//
//        if ("false".equalsIgnoreCase(value) || "no".equalsIgnoreCase(value) || "0".equals(value)) {
//            return false;
//        }
//        return defaultValue;
//    }
//
//    /**
//     * Returns the value of the Java system property with the specified
//     * {@code key}, while falling back to the specified default value if
//     * the property access fails.
//     *
//     * @param key key.
//     * @param defaultValue default value.
//     * @return the property value.
//     *         {@code defaultValue} if there's no such property or if an access to the
//     *         specified property is not allowed.
//     * @since 1.2.6
//     */
//    public static int getInt(String key, int defaultValue) {
//        String value = getProperty(key);
//        if (value == null) {
//            return defaultValue;
//        }
//
//        value = value.trim();
//        try {
//            return Integer.parseInt(value);
//        } catch (Exception e) {
//            // Ignore
//        }
//        return defaultValue;
//    }
//
//    /**
//     * Returns the value of the Java system property with the specified
//     * {@code key}, while falling back to the specified default value if
//     * the property access fails.
//     *
//     * @param key key.
//     * @param defaultValue default value.
//     * @return the property value.
//     *         {@code defaultValue} if there's no such property or if an access to the
//     *         specified property is not allowed.
//     * @since 1.2.6
//     */
//    public static long getLong(String key, long defaultValue) {
//        String value = getProperty(key);
//        if (value == null) {
//            return defaultValue;
//        }
//
//        value = value.trim();
//        try {
//            return Long.parseLong(value);
//        } catch (Exception e) {
//            // Ignore
//        }
//        return defaultValue;
//    }
//
//}
