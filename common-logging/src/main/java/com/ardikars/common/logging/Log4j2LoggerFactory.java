package com.ardikars.common.logging;

import org.apache.logging.log4j.LogManager;

/**
 * Log4j2 logger factory.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
class Log4j2LoggerFactory extends LoggerFactory {

    private static final LoggerFactory INSTANCE = new Log4j2LoggerFactory();

    private Log4j2LoggerFactory() {
        //
    }

    public static LoggerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Logger newInstance(String name) {
        return new Log4j2Logger(LogManager.getLogger(name));
    }

}
