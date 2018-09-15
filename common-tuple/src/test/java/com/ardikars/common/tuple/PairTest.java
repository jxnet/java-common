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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairTest extends BaseTest {

    @Test
    public void pair() {
        Pair<Integer, String> pair = Tuple.of(1, "nol");
        assertEquals(Integer.valueOf(1), pair.getLeft());
        assertEquals("nol", pair.getRight());
    }

}
