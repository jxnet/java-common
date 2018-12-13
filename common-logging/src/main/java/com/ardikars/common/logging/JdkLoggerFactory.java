package com.ardikars.common.logging;

/**
 * @author common 2018/12/09
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public class JdkLoggerFactory extends LoggerFactory {

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
