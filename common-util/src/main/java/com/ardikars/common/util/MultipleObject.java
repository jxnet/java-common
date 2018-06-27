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
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Multiple keys for {@code java.util.Map}
 * @see java.util.Map
 * @see NamedMultipleObject
 * @param <K> key type.
 */
@Immutable
public class MultipleObject<K> implements Serializable {

    private static final long serialVersionUID = -7486266343955776290L;

    private final Set<K> keys;

    protected MultipleObject(Set<K> keys) {
        this.keys = keys;
    }

    /**
     * Create {@code MultiKey} object.
     * @param keys keys.
     * @param <K> key type.
     * @return returns {@code MultiKey} object.
     */
    @SuppressWarnings("unchecked")
    public static <K> MultipleObject<K> of(K... keys) {
        Set<K> keySet = Arrays.asList(keys)
                .stream()
                .parallel()
                .collect(Collectors.toSet());
        return new MultipleObject<>(keySet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MultipleObject)) {
            return false;
        }
        MultipleObject<?> multiKey = (MultipleObject<?>) o;
        return Objects.equals(keys, multiKey.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keys);
    }

    @Override
    public String toString() {
        return String.valueOf(keys);
    }

}
