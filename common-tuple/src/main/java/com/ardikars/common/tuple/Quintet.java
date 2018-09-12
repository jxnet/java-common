package com.ardikars.common.tuple;

import java.io.Serializable;

public interface Quintet<L, BLM, M, BRM, R> extends Serializable {

    L getLeft();

    BLM getBetweenLeftAndMiddle();

    M getMiddle();

    BRM getBetweenRigthAndMiddle();

    R getRight();

}
