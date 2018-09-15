/**
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ardikars.common.tuple.impl;

import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.tuple.Quintet;
import com.ardikars.common.tuple.Tuple;

@Immutable
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
