package com.ardikars.common.util;

import org.junit.Test;


import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class HexsTest extends BaseTest {

    private static final byte[] byteData = new byte[] { (byte) 10, (byte) 43, (byte) 45, (byte) 2, (byte) 5 };

    @Test
    public void parseHexToByteArray() {
        assertEquals(java.util.Arrays.toString(byteData), java.util.Arrays.toString(Hexs.parseHex("0a2b2d0205")));
    }

    @Test
    public void hexDump() {
        String expected =
                "         +-------------------------------------------------+\n" +
                "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |\n" +
                "+--------+-------------------------------------------------+--------+\n" +
                "00000000 | 0a 2b 2d 02 05 | .+-..\n" +
                "+--------+-------------------------------------------------+--------+";
        assertEquals(expected, Hexs.toPrettyHexDump(byteData));
    }

    @Test
    public void bytestoHex() {
        assertEquals("0a2b2d0205", Hexs.toHexString(byteData));
    }

    @Test
    public void bytestoHexWithRange() {
        assertEquals("2b2d02", Hexs.toHexString(byteData, 1, 3));
    }

    @Test
    public void bufferToHex() {
        ByteBuffer buffer = ByteBuffer.wrap(byteData);
        assertEquals("0a2b2d0205", Hexs.toHexString(buffer));
    }

    @Test
    public void bufferToHexWithRange() {
        ByteBuffer buffer = ByteBuffer.wrap(byteData);
        assertEquals("2b2d02", Hexs.toHexString(buffer, 1, 3));
    }

}
