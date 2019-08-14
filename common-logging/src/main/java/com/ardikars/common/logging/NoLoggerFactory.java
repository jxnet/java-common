package com.ardikars.common.logging;

import com.ardikars.common.annotation.Incubating;

@Incubating
class NoLoggerFactory extends LoggerFactory {

    private static final LoggerFactory INSTANCE = new NoLoggerFactory();

    public static LoggerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    Logger newInstance(String name) {
        return new NoLogger(name);
    }

}
