package com.ardikars.common.logging;

/**
 * @author common 2018/12/09
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public abstract class AbstractLogger implements Logger {

    static final String UNEXPECTED_EXCEPTION_MESSAGE;

    private final String name;

    AbstractLogger(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Logger name should be not null or empty.");
        }
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isEnabled(LogLevel level) {
        switch (level) {
            case DEBUG:
                return isDebugEnabled();
            case INFO:
                return isInfoEnabled();
            case WARN:
                return isWarnEnabled();
            case ERROR:
                return isErrorEnabled();
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void log(LogLevel level, String format) {
        switch (level) {
            case DEBUG:
                debug(format);
                break;
            case INFO:
                info(format);
                break;
            case WARN:
                warn(format);
                break;
            case ERROR:
                error(format);
                break;
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void log(LogLevel level, Throwable throwable) {
        switch (level) {
            case DEBUG:
                debug(throwable);
                break;
            case INFO:
                info(throwable);
                break;
            case WARN:
                warn(throwable);
                break;
            case ERROR:
                error(throwable);
                break;
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void log(LogLevel level, String format, Object arg1) {
        switch (level) {
            case DEBUG:
                debug(format, arg1);
                break;
            case INFO:
                info(format, arg1);
                break;
            case WARN:
                warn(format, arg1);
                break;
            case ERROR:
                error(format, arg1);
                break;
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void log(LogLevel level, String format, Object arg1, Object arg2) {
        switch (level) {
            case DEBUG:
                debug(format, arg1, arg2);
                break;
            case INFO:
                info(format, arg1, arg2);
                break;
            case WARN:
                warn(format, arg1, arg2);
                break;
            case ERROR:
                error(format, arg1, arg2);
                break;
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void log(LogLevel level, String format, Object... arguments) {
        switch (level) {
            case DEBUG:
                debug(format, arguments);
                break;
            case INFO:
                info(format, arguments);
                break;
            case WARN:
                warn(format, arguments);
                break;
            case ERROR:
                error(format, arguments);
                break;
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void log(LogLevel level, String format, Throwable throwable) {
        switch (level) {
            case DEBUG:
                debug(format, throwable);
                break;
            case INFO:
                info(format, throwable);
                break;
            case WARN:
                warn(format, throwable);
                break;
            case ERROR:
                error(format, throwable);
                break;
            default:
                throw new Error("Unknown log level");
        }
    }

    @Override
    public void debug(String message) {
        debug(UNEXPECTED_EXCEPTION_MESSAGE, message);
    }

    @Override
    public void debug(Throwable throwable) {
        debug(UNEXPECTED_EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void info(String message) {
        info(UNEXPECTED_EXCEPTION_MESSAGE, message);
    }

    @Override
    public void info(Throwable throwable) {
        info(UNEXPECTED_EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void warn(String message) {
        info(UNEXPECTED_EXCEPTION_MESSAGE, message);
    }

    @Override
    public void warn(Throwable throwable) {
        info(UNEXPECTED_EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void error(String message) {
        error(UNEXPECTED_EXCEPTION_MESSAGE, message);
    }

    @Override
    public void error(Throwable throwable) {
        error(UNEXPECTED_EXCEPTION_MESSAGE, throwable);
    }

    static {
        UNEXPECTED_EXCEPTION_MESSAGE = System.getProperty("common.logging.default", "Unexpected exception:");
    }

}
