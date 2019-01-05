/**
 * Copyright 2017-2019 the original author or authors.
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
import com.ardikars.common.tuple.Quartet;
import com.ardikars.common.tuple.Tuple;

/**
 * Implementations of quertet tuple.
 *
 * @param <L> left.
 * @param <ML> middle left.
 * @param <MR> middle right.
 * @param <R> right.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.0
 */
@Immutable
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
