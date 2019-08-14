package com.ardikars.common.logging;

import org.apache.log4j.LogManager;

@Deprecated
class Log4jLoggerFactory extends LoggerFactory {

    private static final LoggerFactory INSTANCE = new Log4jLoggerFactory();

    private static final boolean HAS_LOG4J;

    public static boolean hasLog4j() {
        return HAS_LOG4J;
    }

    public static LoggerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Logger newInstance(String name) {
        return new Log4jLogger(LogManager.getLogger(name));
    }

    static {
        boolean hasLog4j;
        try {
            Class.forName("org.apache.log4j.Logger");
            hasLog4j = true;
        } catch (ClassNotFoundException e) {
            hasLog4j = false;
        }
        HAS_LOG4J = hasLog4j;
    }

}
