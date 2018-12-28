package com.ardikars.common.logging;

/**
 * Logger factory.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public abstract class LoggerFactory {

    private static volatile LoggerFactory DEFAULT_LOGGER_FACTORY;

    public static LoggerFactory getDefaultLoggerFactory() {
        if (DEFAULT_LOGGER_FACTORY == null) {
            DEFAULT_LOGGER_FACTORY = newDefaultFactory();
        }
        return DEFAULT_LOGGER_FACTORY;
    }

    public static Logger getLogger(String name) {
        return getDefaultLoggerFactory().newInstance(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(Object object) {
        return getLogger(object.getClass());
    }

    private static LoggerFactory newDefaultFactory() {
        LoggerFactory loggerFactory;
        try {
            if (Slf4jLoggerFactory.hasSlf4j()) {
                loggerFactory = Slf4jLoggerFactory.getInstance();
            } else if (Log4j2LoggerFactory.hasLog4j2()) {
                loggerFactory = Log4j2LoggerFactory.getInstance();
            } else {
                loggerFactory = JdkLoggerFactory.getInstance();
            }
        } catch (Throwable e) {
            throw e;
        }
        return loggerFactory;
    }

    public abstract Logger newInstance(String name);

}
