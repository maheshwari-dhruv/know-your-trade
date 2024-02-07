package org.blog.knowyourtrade.util;

import org.slf4j.Logger;

public class LogUtils {

    public static void trace(Logger log, String message, Object... args) {
        trace(log, "", message, args);
    }

    public static void trace(Logger log, String code, String message, Object... args) {
        log.trace(message, args);
    }

    public static void debug(Logger log, String message, Object... args) {
        debug(log, "", message, args);
    }

    public static void debug(Logger log, String code, String message, Object... args) {
        log.debug(message, args);
    }

    public static void info(Logger log, String message, Object... args) {
        info(log, "", message, args);
    }

    public static void info(Logger log, String code, String message, Object... args) {
        log.info(message, args);
    }

    public static void warn(Logger log, String message, Object... args) {
        warn(log, "", message, args);
    }

    public static void warn(Logger log, String code, String message, Object... args) {
        log.warn(message, args);
    }

    public static void error(Logger log, String message, Object... args) {
        error(log, "", message, null, args);
    }

    public static void error(Logger log, String message, Throwable stacktrace, Object... args) {
        error(log, "", message, stacktrace, args);
    }

    public static void error(Logger log, String code, String message, Throwable stacktrace, Object... args) {
        log.error(message, args);
        if (stacktrace != null) {
            log.error(stacktrace.getMessage(), stacktrace);
        }
    }
}
