package com.ardikars.common.tuple;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class QuartetTest extends BaseTest {

    @Test
    public void quartet() {
        Quartet<Integer, Float, Long, String> quartet = Tuple
                .of(1, 1.1F, 1L, "nol");
        assertEquals(Integer.valueOf(1), quartet.getLeft());
        assertEquals(Float.valueOf(1.1F), quartet.getMiddleLeft());
        assertEquals(Integer.valueOf(1), quartet.getLeft());
        assertEquals(Integer.valueOf(1), quartet.getLeft());
    }

}
