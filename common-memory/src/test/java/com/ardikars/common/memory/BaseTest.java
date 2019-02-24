package com.ardikars.common.memory;

import java.security.SecureRandom;
import java.util.Random;

abstract class BaseTest {

    Random RANDOM = new SecureRandom();

    int BIT_SIZE = 2;
    int BYTE_SIZE = Byte.SIZE / Byte.SIZE;
    int SHORT_SIZE = Short.SIZE / Byte.SIZE;
    int INT_SIZE = Integer.SIZE / Byte.SIZE;
    int LONG_SIZE = Long.SIZE / Byte.SIZE;

    int DEFAULT_CAPACITY = 32; //LONG_SIZE * RANDOM.nextInt(Integer.MAX_VALUE / (BIT_SIZE * INT_SIZE));
    int DEFAULT_MAX_CAPACITY = DEFAULT_CAPACITY + INT_SIZE;

    byte[] DUMMY = new byte[] { 0, 1, 2, 3, 4};

}
