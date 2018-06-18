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

package com.ardikars.java.common.util;

/**
 * Common base class for {@code TwoKeyMap}.
 * @see Enum
 * @see TwoKeyMap
 * Example:
 * <pre>
 *     not yet documented.
 * </pre>
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
public abstract class NamedTwoKeyMap<T extends TwoKeyMap, U, V extends NamedTwoKeyMap<T, U, ?>> {

    private final TwoKeyMap<T, U> key;
    private final String name;

    protected NamedTwoKeyMap(T firstKey, U secondKey, String name) {
        this.key = TwoKeyMap.newInstance(firstKey, secondKey);
        this.name = name;
    }

    /**
     * Returns the key of this {@code NamedTwoKeyMap} object.
     * @return returns the key of this {@code NamedTwoKeyMap} object.
     */
    public TwoKeyMap<T, U> getKey() {
        return key;
    }

    /**
     * Returns the name of this {@code NamedTwoKeyMap} object.
     * @return returns the name of this {@code NamedTwoKeyMap} object.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (!(obj instanceof NamedTwoKeyMap)) {
            return false;
        }
        return this.key.equals(this.getClass().cast(obj).getKey());
    }

    @Override
    public int hashCode() {
        return 17 * 37 + this.getKey().hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Key: ").append(getKey().toString())
                .append(", Name: ").append(this.getName().toString())
                .append("]").toString();
    }

}
