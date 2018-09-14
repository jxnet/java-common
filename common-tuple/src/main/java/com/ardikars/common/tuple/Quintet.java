package com.ardikars.common.tuple;

public interface Quintet<L, BLM, M, BRM, R> {

    L getLeft();

    BLM getBetweenLeftAndMiddle();

    M getMiddle();

    BRM getBetweenRigthAndMiddle();

    R getRight();

}
