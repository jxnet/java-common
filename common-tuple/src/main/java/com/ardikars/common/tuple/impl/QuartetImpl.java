package com.ardikars.common.tuple.impl;

import com.ardikars.common.tuple.Quartet;
import com.ardikars.common.tuple.Tuple;

public class QuartetImpl<L, ML, MR, R> extends Tuple implements Quartet<L, ML, MR, R> {

    private final L left;
    private final ML middleLeft;
    private final MR middleRight;
    private final R right;

    public QuartetImpl(L left, ML middleLeft, MR middleRight, R right) {
        this.left = left;
        this.middleLeft = middleLeft;
        this.middleRight = middleRight;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public ML getMiddleLeft() {
        return middleLeft;
    }

    @Override
    public MR getMiddleRight() {
        return middleRight;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public int size() {
        return 4;
    }

}
