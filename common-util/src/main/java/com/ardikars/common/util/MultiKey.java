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
 * @See com.ardikars.common.util.NamedMultiKeyMap
 * @param <K> key type.
 */
@Immutable
public class MultiKey<K> implements Serializable {

    private static final long serialVersionUID = -7486266343955776290L;

    private final Set<K> keys;

    private MultiKey(Set<K> keys) {
        this.keys = keys;
    }

    public static <K> MultiKey<K> of(K... keys) {
        Set<K> keySet = Arrays.asList(keys)
                .stream()
                .parallel()
                .collect(Collectors.toSet());
        return new MultiKey<>(keySet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiKey)) return false;
        MultiKey<?> multiKey = (MultiKey<?>) o;
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
