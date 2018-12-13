package com.ardikars.common.logging;

/**
 * @author common 2018/12/09
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
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
