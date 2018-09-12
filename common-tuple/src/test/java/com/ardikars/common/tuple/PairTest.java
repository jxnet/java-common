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
