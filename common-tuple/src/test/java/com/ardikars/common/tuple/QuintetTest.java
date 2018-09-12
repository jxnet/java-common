package com.ardikars.common.tuple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuintetTest extends BaseTest {

    @Test
    public void quintet() {
        Quintet<Integer, Float, Long, Double, String> quintet = Tuple
                .of(1, 1.1F, 1L, 1.1D, "nol");
        assertEquals(Integer.valueOf(1), quintet.getLeft());
        assertEquals(Float.valueOf(1.1F), quintet.getBetweenLeftAndMiddle());
        assertEquals(Long.valueOf(1L), quintet.getMiddle());
        assertEquals(Double.valueOf(1.1D), quintet.getBetweenRigthAndMiddle());
        assertEquals("nol", quintet.getRight());
    }

}
