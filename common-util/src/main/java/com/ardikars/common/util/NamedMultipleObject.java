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

import java.util.Objects;

/**
 * Common base class for dynamic named multi key (enum like).
 * @param <T> multiple key.
 * @param <U> named multiple key.
 */
public abstract class NamedMultipleObject<T extends MultipleObject, U extends NamedMultipleObject<T, ?>> implements ObjectName<T, U> {

    private final T value;
    private final String name;

    protected NamedMultipleObject(T multiKey, String name) {
        this.value = multiKey;
        this.name = name;
    }

    /**
     * Returns the number of this {@code NamedMultiKey} object.
     * @return returns the number of this {@code NamedMultiKey} object.
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * Returns the name of this {@code NamedMultiKey} object.
     * @return returns the name of this {@code NamedMultiKey} object.
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NamedMultipleObject)) {
            return false;
        }
        NamedMultipleObject<?, ?> that = (NamedMultipleObject<?, ?>) o;
        return Objects.equals(getValue(), that.getValue())
                && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getName());
    }

    @Override
    public String toString() {
        return new StringBuilder("NamedMultiKey{")
                .append("value=").append(value)
                .append(", name='").append(name).append('\'')
                .append('}').toString();
    }

}
