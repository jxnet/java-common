package com.ardikars.common.tuple.impl;

import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.tuple.Triplet;
import com.ardikars.common.tuple.Tuple;

@Immutable
public class TripletImpl<L, M, R> extends Tuple implements Triplet<L, M, R> {

    private final L left;
    private final M middle;
    private final R right;

    public TripletImpl(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public M getMiddle() {
        return middle;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TripletImpl{");
        sb.append("left=").append(left);
        sb.append(", middle=").append(middle);
        sb.append(", right=").append(right);
        sb.append('}');
        return sb.toString();
    }

}
