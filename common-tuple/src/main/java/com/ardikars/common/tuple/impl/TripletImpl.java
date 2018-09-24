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
import com.ardikars.common.tuple.Triplet;
import com.ardikars.common.tuple.Tuple;

/**
 * Implementation of triplet tuple
 *
 * @param <L> left.
 * @param <M> middle.
 * @param <R> right.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.0
 */
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
