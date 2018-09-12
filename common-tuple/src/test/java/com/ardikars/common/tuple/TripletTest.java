package com.ardikars.common.tuple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TripletTest extends BaseTest {

    @Test
    public void triplet() {
        Triplet<Integer, Double, String> triplet = Tuple.of(1, 0.3, "nol");
        assertEquals(Integer.valueOf(1), triplet.getLeft());
        assertEquals(Double.valueOf(0.3), triplet.getMiddle());
        assertEquals("nol", triplet.getRight());
    }

}
