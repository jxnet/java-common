package com.ardikars.common.tuple.impl;

import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;

@Immutable
public class PairImpl<L, R> extends Tuple implements Pair<L, R> {

    private final L left;
    private final R right;

    public PairImpl(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PairImpl{");
        sb.append("left=").append(left);
        sb.append(", right=").append(right);
        sb.append('}');
        return sb.toString();
    }

}
