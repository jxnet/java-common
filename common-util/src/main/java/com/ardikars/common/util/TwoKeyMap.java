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

import com.ardikars.common.annotation.Immutable;

/**
 * An object that maps two key to values. A {@code TwoKeyMap} cannot contain duplicate keys;
 * each key can map to at most one value.
 * @see java.util.Map
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Immutable
public final class TwoKeyMap<T, U> {

    private final T firstKey;
    private final U secondKey;

    private TwoKeyMap(T firstKey, U secondKey) {
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    /**
     * Create {@code TwoKeyMap} object.
     * @param firstKey first key.
     * @param secondKey second key.
     * @param <T> first key type.
     * @param <U> second key type.
     * @return return an new {@code TwoKeyMap} object.
     */
    public static <T, U> TwoKeyMap<T, U> newInstance(T firstKey, U secondKey) {
        return new TwoKeyMap<T, U>(firstKey, secondKey);
    }

    /**
     * Returns first key.
     * @return returns first key.
     */
    public T getFirstKey() {
        return this.firstKey;
    }

    /**
     * Returns second key.
     * @return returns second key.
     */
    public U getSecondKey() {
        return this.secondKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (!(obj instanceof TwoKeyMap)) {
            return false;
        }
        return (this.firstKey.equals(this.getClass().cast(obj).getFirstKey())
                && this.secondKey.equals(this.getClass().cast(obj).getSecondKey()));
    }

    @Override
    public int hashCode() {
        return 17 * 37 + this.getFirstKey().hashCode() + this.getSecondKey().hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[First Key: ").append(this.getFirstKey().toString())
                .append(", Second Key: ").append(this.getSecondKey().toString())
                .append("]").toString();
    }

}
