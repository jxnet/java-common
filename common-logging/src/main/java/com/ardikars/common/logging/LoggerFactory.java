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
           loggerFactory = Slf4jLoggerFactory.getInstance();
        } catch (Throwable ignore1) {
            try {
                loggerFactory = Log4j2LoggerFactory.getInstance();
            } catch (Throwable ignore2) {
                loggerFactory = JdkLoggerFactory.getInstance();
            }
        }
        return loggerFactory;
    }

    public abstract Logger newInstance(String name);

}
