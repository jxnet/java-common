package com.ardikars.common.logging;

import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * JDK logger implementation.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
class JdkLogger extends AbstractLogger {

    private static final String SELF = JdkLogger.class.getName();
    private static final String SUPER = AbstractLogger.class.getName();

    private final transient Logger logger;
    private final Formatter formatter = new Formatter();

    JdkLogger(Logger logger) {
        super(logger.getName());
        this.logger = logger;
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.ALL);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public void debug(String format, Object arg1) {
        if (logger.isLoggable(Level.ALL)) {
            String msg = formatter.format(format, arg1).toString();
            log(SELF, Level.ALL, msg, null);
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.ALL)) {
            String msg = formatter.format(format, arg1, arg2).toString();
            log(SELF, Level.ALL, msg, null);
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (logger.isLoggable(Level.ALL)) {
            String msg = formatter.format(format, arguments).toString();
            log(SELF, Level.ALL, msg, null);
        }
    }

    @Override
    public void debug(String format, Throwable throwable) {
        if (logger.isLoggable(Level.ALL)) {
            String msg = formatter.format(format, throwable).toString();
            log(SELF, Level.ALL, msg, throwable);
        }
    }

    @Override
    public void info(String format, Object obj1) {
        if (logger.isLoggable(Level.INFO)) {
            String msg = formatter.format(format, obj1).toString();
            log(SELF, Level.INFO, msg, null);
        }
    }

    @Override
    public void info(String format, Object obj1, Object obj2) {
        if (logger.isLoggable(Level.INFO)) {
            String msg = formatter.format(format, obj1, obj2).toString();
            log(SELF, Level.INFO, msg, null);
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        if (logger.isLoggable(Level.INFO)) {
            String msg = formatter.format(format, arguments).toString();
            log(SELF, Level.INFO, msg, null);
        }
    }

    @Override
    public void info(String format, Throwable throwable) {
        if (logger.isLoggable(Level.ALL)) {
            String msg = formatter.format(format, throwable).toString();
            log(SELF, Level.ALL, msg, throwable);
        }
    }

    @Override
    public void warn(String format, Object arg1) {
        if (logger.isLoggable(Level.WARNING)) {
            String msg = formatter.format(format, arg1).toString();
            log(SELF, Level.WARNING, msg, null);
        }
    }

    @Override
    public void warn(String format, Object arg1, Object obj2) {
        if (logger.isLoggable(Level.WARNING)) {
            String msg = formatter.format(format, arg1, obj2).toString();
            log(SELF, Level.WARNING, msg, null);
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (logger.isLoggable(Level.WARNING)) {
            String msg = formatter.format(format, arguments).toString();
            log(SELF, Level.WARNING, msg, null);
        }
    }

    @Override
    public void warn(String format, Throwable throwable) {
        if (logger.isLoggable(Level.WARNING)) {
            String msg = formatter.format(format, throwable).toString();
            log(SELF, Level.WARNING, msg, throwable);
        }
    }

    @Override
    public void error(String format, Object obj1) {
        if (logger.isLoggable(Level.SEVERE)) {
            String msg = formatter.format(format, obj1).toString();
            log(SELF, Level.SEVERE, msg, null);
        }
    }

    @Override
    public void error(String format, Object obj1, Object obj2) {
        if (logger.isLoggable(Level.SEVERE)) {
            String msg = formatter.format(format, obj1, obj2).toString();
            log(SELF, Level.SEVERE, msg, null);
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        if (logger.isLoggable(Level.SEVERE)) {
            String msg = formatter.format(format, arguments).toString();
            log(SELF, Level.SEVERE, msg, null);
        }
    }

    @Override
    public void error(String format, Throwable throwable) {
        if (logger.isLoggable(Level.SEVERE)) {
            String msg = formatter.format(format, throwable).toString();
            log(SELF, Level.SEVERE, msg, throwable);
        }
    }

    private void log(String callerFQCN, Level level, String msg, Throwable t) {
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(name());
        record.setThrown(t);
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        int selfIndex = -1;
        for (int i = 0; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (className.equals(callerFQCN) || className.equals(SUPER)) {
                selfIndex = i;
                break;
            }
        }

        int found = -1;
        for (int i = selfIndex + 1; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (!(className.equals(callerFQCN) || className.equals(SUPER))) {
                found = i;
                break;
            }
        }

        if (found != -1) {
            StackTraceElement ste = steArray[found];
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
        logger.log(record);
    }

}
