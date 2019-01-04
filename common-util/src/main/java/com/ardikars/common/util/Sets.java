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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Set helper
 * @since 1.2.3
 */
@Helper
public final class Sets {

    private static final float HASHSET_DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Create an {@link java.util.HashSet} with its initialCapacity calculated
     * to minimize rehash operations
     * @param expectedMapSize expected map size
     * @param <E> type.
     * @return returns {@link Set} object.
     */
    public static <E> Set<E> createHashSet(int expectedMapSize) {
        final int initialCapacity = (int) (expectedMapSize / HASHSET_DEFAULT_LOAD_FACTOR) + 1;
        return new HashSet<E>(initialCapacity, HASHSET_DEFAULT_LOAD_FACTOR);
    }

    /**
     * Create an {@link java.util.LinkedHashSet} with its initialCapacity calculated
     * to minimize rehash operations
     * @param expectedMapSize expected map size
     * @param <E> type.
     * @return returns {@link Set} object.
     */
    public static <E> Set<E> createLinkedHashSet(int expectedMapSize) {
        final int initialCapacity = (int) (expectedMapSize / HASHSET_DEFAULT_LOAD_FACTOR) + 1;
        return new LinkedHashSet<E>(initialCapacity, HASHSET_DEFAULT_LOAD_FACTOR);
    }

}
