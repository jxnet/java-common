package com.ardikars.common.util;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class StringsTest extends BaseTest {

    private static final byte[] byteData = new byte[] { (byte) 10, (byte) 43, (byte) 45, (byte) 2, (byte) 5 };
    private static final short[] shortData = new short[] { (short) 476, (short) 45832, (short) 455632, (short) 45522, (short) 45432 };
    private static final int[] intData = new int[] { 204743647, 2047483147, 2047883646, 2046483647, 2047483645 };

    @Test
    public void byteToHexString() {
        assertEquals("0a", Strings.toHexString(byteData[0]));
    }

    @Test
    public void byteArrayToHexString() {
        assertEquals("0a2b2d0205", Strings.toHexString(byteData));
    }

    @Test
    public void byteArrayToHexStringWithRange() {
        assertEquals("2b2d02", Strings.toHexString(byteData, 1, byteData.length - 2));
    }

    @Test
    public void shortToHexString() {
        assertEquals("01dc", Strings.toHexString(shortData[0]));
    }

    @Test
    public void shortArrayToHexString() {
        assertEquals("01dcb308f3d0b1d2b178", Strings.toHexString(shortData));
    }

    @Test
    public void shortArrayToHexStringWithRange() {
        assertEquals("b308f3d0b1d2", Strings.toHexString(shortData, 1, shortData.length - 2));
    }

    @Test
    public void intToHexString() {
        assertEquals("0c3423df", Strings.toHexString(intData[0]));
    }

    @Test
    public void intArrayToHexString() {
        assertEquals("0c3423df7a0a1d0b7a10397e79fadcbf7a0a1efd", Strings.toHexString(intData));
    }

    @Test
    public void intArrayToHexStringWithRange() {
        assertEquals("7a0a1d0b7a10397e79fadcbf", Strings.toHexString(intData, 1, intData.length - 2));
    }

}
