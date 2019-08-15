package com.ardikars.common.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Deprecated
class JdkLogger extends AbstractLogger {

    private static final String SELF = JdkLogger.class.getName();
    private static final String SUPER = AbstractLogger.class.getName();

    private final transient Logger logger;

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
            FormattingTuple tuple = MessageFormatter.format(format, arg1);
            log(SELF, Level.ALL, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.ALL)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(SELF, Level.ALL, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void debug(String format, Object... args) {
        if (logger.isLoggable(Level.ALL)) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, args);
            log(SELF, Level.ALL, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void debug(String format, Throwable throwable) {
        if (logger.isLoggable(Level.ALL)) {
            log(SELF, Level.ALL, format, throwable);
        }
    }

    @Override
    public void info(String format, Object obj1) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple tuple = MessageFormatter.format(format, obj1);
            log(SELF, Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void info(String format, Object obj1, Object obj2) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple tuple = MessageFormatter.format(format, obj1, obj2);
            log(SELF, Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void info(String format, Object... args) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, args);
            log(SELF, Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void info(String format, Throwable throwable) {
        if (logger.isLoggable(Level.ALL)) {
            log(SELF, Level.ALL, format, throwable);
        }
    }

    @Override
    public void warn(String format, Object arg1) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1);
            log(SELF, Level.WARNING, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String format, Object arg1, Object obj2) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, obj2);
            log(SELF, Level.WARNING, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String format, Object... args) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, args);
            log(SELF, Level.WARNING, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String format, Throwable throwable) {
        if (logger.isLoggable(Level.WARNING)) {
            log(SELF, Level.WARNING, format, throwable);
        }
    }

    @Override
    public void error(String format, Object obj1) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple tuple = MessageFormatter.format(format, obj1);
            log(SELF, Level.SEVERE, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void error(String format, Object obj1, Object obj2) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple tuple = MessageFormatter.format(format, obj1, obj2);
            log(SELF, Level.SEVERE, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void error(String format, Object... args) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, args);
            log(SELF, Level.SEVERE, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void error(String format, Throwable throwable) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(SELF, Level.SEVERE, format, throwable);
        }
    }

    private void log(String callerFqcn, Level level, String msg, Throwable t) {
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(name());
        record.setThrown(t);
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        int selfIndex = -1;
        for (int i = 0; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (className.equals(callerFqcn) || className.equals(SUPER)) {
                selfIndex = i;
                break;
            }
        }

        int found = -1;
        for (int i = selfIndex + 1; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (!(className.equals(callerFqcn) || className.equals(SUPER))) {
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
