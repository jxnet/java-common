package com.ardikars.common.logging;

/**
 * @author common 2018/12/09
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
public interface Logger {

    String name();

    boolean isEnabled(LogLevel level);

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();

    void log(LogLevel level, String message);

    void log(LogLevel level, String format, Object arg1);

    void log(LogLevel level, String format, Object arg1, Object arg2);

    void log(LogLevel level, String format, Object... arguments);

    void log(LogLevel level, Throwable throwable);

    void log(LogLevel level, String format, Throwable throwables);

    void debug(String message);

    void debug(Throwable throwable);

    void debug(String format, Object arg1);

    void debug(String format, Object arg1, Object arg2);

    void debug(String format, Object... arguments);

    void debug(String format, Throwable throwable);

    void info(String message);

    void info(Throwable throwable);

    void info(String format, Object obj1);

    void info(String format, Object obj1, Object obj2);

    void info(String format, Object... arguments);

    void info(String format, Throwable throwable);

    void warn(String message);

    void warn(Throwable throwable);

    void warn(String format, Object arg1);

    void warn(String format, Object arg1, Object obj2);

    void warn(String format, Object... arguments);

    void warn(String format, Throwable throwable);

    void error(String message);

    void error(Throwable throwable);

    void error(String format, Object obj1);

    void error(String format, Object obj1, Object obj2);

    void error(String format, Object... arguments);

    void error(String format, Throwable throwable);

}
