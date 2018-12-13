package com.ardikars.common.constant;

import com.ardikars.common.annotation.Incubating;

import java.math.BigInteger;

/**
 * @author common 2018/12/01
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
@Incubating
public final class Integer {

    /**
     * (2 ** 8 / 2) - 1 = 127
     */
    public static final int MAX_INT8_VALUE = (1 << 7) - 1;

    /**
     * (2 ** 16 / 2) - 1 = 32767
     */
    public static final int MAX_INT16_VALUE = (1 << 15) - 1;

    /**
     * (2 ** 32 / 2) - 1 = 2147483647
     */
    public static final int MAX_INT32_VALUE = (1 << 31) - 1;

    /**
     * (2 ** 64 / 2) - 1 = 9223372036854775807
     */
    public static final long MAX_INT64_VALUE = (1 << 63) - 1;

    /**
     * (2 ** 8) - 1 = 255
     */
    public static final int MAX_UINT8_VALUE = (1 << 8) - 1;

    /**
     * (2 ** 16) - 1 = 65535
     */
    public static final int MAX_UINT16_VALUE = (1 << 16) - 1;

    /**
     * (2 ** 32) - 1 = 4294967295
     */
    public static final long MAX_UINT32_VALUE = (1 << 32) - 1;

    /**
     * (2 ** 64) - 1 = 18446744073709551615
     */
    public static final BigInteger MAX_UINT64_VALUE = new BigInteger("18446744073709551615");

    /**
     * ((2 ** 8 / 2) - 1) * -1 = -127
     */
    public static final int MIN_INT8_VALUE = -1 << 7;

    /**
     * ((2 ** 16 / 2) - 1) * -1 = -32767
     */
    public static final int MIN_INT16_VALUE = -1 << 15;

    /**
     * ((2 ** 32 / 2) - 1) * -1 = -2147483647
     */
    public static final int MIN_INT32_VALUE = -1 << 31;

    /**
     * ((2 ** 64 / 2) - 1) * -1 = -9223372036854775807
     */
    public static final long MIN_INT64_VALUE = -1 << 63;

    /**
     * 0
     */
    public static final int MIN_UINT8_VALUE = 0;

    /**
     * 0
     */
    public static final int MIN_UINT16_VALUE = 0;

    /**
     * 0
     */
    public static final long MIN_UINT32_VALUE = 0;

    /**
     * 0
     */
    public static final BigInteger MIN_UINT64_VALUE = new BigInteger("0");

}
