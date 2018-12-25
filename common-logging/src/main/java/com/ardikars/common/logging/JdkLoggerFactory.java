package com.ardikars.common.logging;

/**
 * JDK Logger Factory.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
class JdkLoggerFactory extends LoggerFactory {

    private static final LoggerFactory INSTANCE = new JdkLoggerFactory();

    private JdkLoggerFactory() {
        //
    }

    public static LoggerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Logger newInstance(String name) {
        return new JdkLogger(java.util.logging.Logger.getLogger(name));
    }

}
