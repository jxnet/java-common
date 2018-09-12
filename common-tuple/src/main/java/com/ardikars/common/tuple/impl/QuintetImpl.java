package com.ardikars.common.tuple.impl;

import com.ardikars.common.tuple.Quintet;
import com.ardikars.common.tuple.Tuple;

public class QuintetImpl<L, BLM, M, BRM, R> extends Tuple implements Quintet<L, BLM, M, BRM, R> {

    private final L left;
    private final BLM betweenLeftAndMiddle;
    private final M middle;
    private final BRM betweenRightAndMiddle;
    private final R right;

    public QuintetImpl(L left, BLM betweenLeftAndMiddle, M middle, BRM betweenRightAndMiddle, R right) {
        this.left = left;
        this.betweenLeftAndMiddle = betweenLeftAndMiddle;
        this.middle = middle;
        this.betweenRightAndMiddle = betweenRightAndMiddle;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public BLM getBetweenLeftAndMiddle() {
        return betweenLeftAndMiddle;
    }

    @Override
    public M getMiddle() {
        return middle;
    }

    @Override
    public BRM getBetweenRigthAndMiddle() {
        return betweenRightAndMiddle;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuintetImpl{");
        sb.append("left=").append(left);
        sb.append(", betweenLeftAndMiddle=").append(betweenLeftAndMiddle);
        sb.append(", middle=").append(middle);
        sb.append(", betweenRightAndMiddle=").append(betweenRightAndMiddle);
        sb.append(", right=").append(right);
        sb.append('}');
        return sb.toString();
    }

}
