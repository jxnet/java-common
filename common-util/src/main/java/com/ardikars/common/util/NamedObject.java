/**
 * Copyright 2017-2019 the original author or authors.
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

/**
 * Common base class for dynamic named number (enum like).
 *
 * @see Enum
 * @see Object
 * @param <T> object.
 * @param <U> named object.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
public abstract class NamedObject<T, U extends NamedObject<T, ?>> implements ObjectName<T, U> {

    private static final long serialVersionUID = -2413391980553692553L;

    private final T value;
    private final String name;

    protected NamedObject(T value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Returns the value of this {@code NamedObject} object.
     * @return returns the value of this {@code NamedObject} object.
     */
    @Override
    public T getValue() {
        return this.value;
    }

    /**
     * Returns the name of this {@code NamedObject} object.
     * @return returns the name of this {@code NamedObject} object.
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (!(obj instanceof NamedObject)) {
            return false;
        }
        return this.value.equals(this.getClass().cast(obj).getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder("[Value: ")
                .append(this.value)
                .append(", Name: ")
                .append(this.name)
                .append("]").toString();
    }

}
