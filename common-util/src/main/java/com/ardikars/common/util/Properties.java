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

package com.ardikars.common.util;

import com.ardikars.common.annotation.Helper;
import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Property utils.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.3
 */
@Helper
public final class Properties {

    private static final Logger LOGGER = LoggerFactory.getLogger(Properties.class);

    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     * @param key key.
     * @return returns the property value. null if there's no such property or if an access to the
     *         specified property is not allowed.
     * @since 1.2.3
     */
    public static String getProperty(final String key) {
        return getProperty(key, null);
    }

    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     * @param key key.
     * @param defaultValue default value.
     * @return the property value.
     *         {@code defaultValue} if there's no such property or if an access to the
     *         specified property is not allowed.
     * @since 1.2.3
     */
    public static String getProperty(final String key, String defaultValue) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Property key should be not null");
        }
        String value = null;
        try {
            if (System.getSecurityManager() == null) {
                value = System.getProperty(key);
            } else {
                value = AccessController.doPrivileged(new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return System.getProperty(key);
                    }
                });
            }
        } catch (SecurityException e) {
            LOGGER.warn("Unable to retrieve a system property '{}'; default values will be used: {}", key, e.getMessage());
        }
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

}
