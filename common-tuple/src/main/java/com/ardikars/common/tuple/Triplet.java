package com.ardikars.common.tuple;

import java.io.Serializable;

public interface Triplet<L, M, R> extends Serializable {

    L getLeft();

    M getMiddle();

    R getRight();

}
