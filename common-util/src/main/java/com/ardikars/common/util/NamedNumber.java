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

/**
 * Common base class for dynamic named number (enum like).
 * @see Enum
 * @see Number
 * Example:
 * <pre>
 * public final class HttpStatusCode extends NamedNumber&lt;Integer, HttpStatusCode&gt;} {
 *
 *      public static final HttpStatusCode NOT_FOUND =
 *          new HttpStatusCode(404, "Not found.");
 *
 *      public static final HttpStatusCode OK =
 *          new HttpStatusCode(20, "Ok.");
 *
 *      public static final HttpStatusCode UNKNOWN =
 *          new HttpStatusCode(0, "Unknown Http Status Code.");
 *
 *      public HttpStatusCode(Integer value, String name) {
 *          super(value, name);
 *      }
 *
 *      private static final Map&lt;Integer, HttpStatusCode&gt; registry
 *          = new HashMap&lt;&gt;();
 *
 *      public static final HttpStatusCode register(final HttpStatusCode httpStatusCode) {
 *          registry.put(httpStatusCode.getValue(), httpStatusCode);
 *          return httpStatusCode;
 *      }
 *
 *      public static final HttpStatusCode valueOf(final int rawValue) {
 *          HttpStatusCode httpStatusCode = registry.get(rawValue);
 *          if (httpStatusCode == null) {
 *              return UNKNOWN;
 *          }
 *          return httpStatusCode;
 *      }
 *
 *      static {
 *          registry.put(NOT_FOUND.getValue(), NOT_FOUND);
 *          registry.put(OK.getValue(), OK);
 *          registry.put(UNKNOWN.getValue(), UNKNOWN);
 *      }
 *
 *  }
 *  </pre>
 * @param <T> number.
 * @param <U> named number.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
public abstract class NamedNumber<T extends Number, U extends NamedNumber<T, ?>> implements ObjectName<T, U> {

    private static final long serialVersionUID = -7754849362562086047L;

    private final T value;
    private final String name;

    protected NamedNumber(T value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Returns the number of this {@code NamedNumber} object.
     * @return returns the number of this {@code NamedNumber} object.
     */
    @Override
    public T getValue() {
        return this.value;
    }

    /**
     * Returns the name of this {@code NamedNumber} object.
     * @return returns the name of this {@code NamedNumber} object.
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
        if (!(obj instanceof NamedNumber)) {
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
