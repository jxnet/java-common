package com.ardikars.common.logging;

/**
 * @author common 2018/12/11
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public class Slf4jLoggerFactory extends LoggerFactory {

    private static final LoggerFactory INSTANCE = new Slf4jLoggerFactory();

    private Slf4jLoggerFactory() {
        //
    }

    public static LoggerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Logger newInstance(String name) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

}
