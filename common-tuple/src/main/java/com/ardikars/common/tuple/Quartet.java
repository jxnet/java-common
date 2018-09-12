package com.ardikars.common.tuple;

import java.io.Serializable;

public interface Quartet<L, ML, MR, R> extends Serializable {

    L getLeft();

    ML getMiddleLeft();

    MR getMiddleRight();

    R getRight();

}
