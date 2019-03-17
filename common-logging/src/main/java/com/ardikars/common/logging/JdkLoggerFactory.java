package com.ardikars.common.logging;

class JdkLoggerFactory extends LoggerFactory {

    private static final LoggerFactory INSTANCE = new JdkLoggerFactory();

    private static final boolean HAS_JDK_LOGGER;

    private JdkLoggerFactory() {
        //
    }

    public static boolean hasJdkLogger() {
        return HAS_JDK_LOGGER;
    }

    public static LoggerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Logger newInstance(String name) {
        return new JdkLogger(java.util.logging.Logger.getLogger(name));
    }

    static {
        boolean hasJdkLogger;
        try {
            Class.forName("java.util.logging.Logger");
            hasJdkLogger = true;
        } catch (ClassNotFoundException e) {
            hasJdkLogger = false;
        }
        HAS_JDK_LOGGER = hasJdkLogger;
    }

}
