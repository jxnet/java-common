package com.ardikars.common.util;

import java.util.Objects;

/**
 * Common base class for dynamic named multi key (enum like).
 * @param <T> multiple key.
 * @param <U> named multiple key.
 */
public abstract class NamedMultiKey<T extends MultiKey, U extends NamedMultiKey<T, ?>> {

    private final T value;
    private final String name;

    protected NamedMultiKey(T multiKey, String name) {
        this.value = multiKey;
        this.name = name;
    }

    /**
     * Returns the number of this {@code NamedMultiKey} object.
     * @return returns the number of this {@code NamedMultiKey} object.
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the name of this {@code NamedMultiKey} object.
     * @return returns the name of this {@code NamedMultiKey} object.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedMultiKey)) return false;
        NamedMultiKey<?, ?> that = (NamedMultiKey<?, ?>) o;
        return Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getName(), that.getName());
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
