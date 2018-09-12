package com.ardikars.common.tuple;

import java.io.Serializable;

public interface Pair<L, R> extends Serializable {

    L getLeft();

    R getRight();

}
