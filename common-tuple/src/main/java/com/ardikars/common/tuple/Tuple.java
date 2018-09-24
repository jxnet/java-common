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

package com.ardikars.common.tuple;

import com.ardikars.common.annotation.Helper;
import com.ardikars.common.tuple.impl.PairImpl;
import com.ardikars.common.tuple.impl.QuartetImpl;
import com.ardikars.common.tuple.impl.QuintetImpl;
import com.ardikars.common.tuple.impl.TripletImpl;

import java.io.Serializable;

/**
 * Helper class for create tuple.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.2.0
 */
@Helper
public abstract class Tuple implements Serializable {

    public abstract int size();

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new PairImpl<>(left, right);
    }

    public static <L, M, R> Triplet<L, M, R> of(L left, M middle, R right) {
        return new TripletImpl<>(left, middle, right);
    }

    public static <L, ML, MR, R> Quartet<L, ML, MR, R> of(L left, ML middleLeft, MR middleRight, R right) {
        return new QuartetImpl<>(left, middleLeft, middleRight, right);
    }

    public static <L, BLM, M, BRM, R> Quintet<L, BLM, M, BRM, R> of(L left, BLM betweenLeftAndMiddle, M middle, BRM betweenRightAndMiddle, R right) {
        return new QuintetImpl<>(left, betweenLeftAndMiddle, middle, betweenRightAndMiddle, right);
    }

}
