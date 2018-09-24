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
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;

/**
 * Implementation of pair tuple.
 *
 * @param <L> left.
 * @param <R> right.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.0
 */
@Immutable
public class PairImpl<L, R> extends Tuple implements Pair<L, R> {

    private final L left;
    private final R right;

    public PairImpl(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PairImpl{");
        sb.append("left=").append(left);
        sb.append(", right=").append(right);
        sb.append('}');
        return sb.toString();
    }

}
