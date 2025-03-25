package cn.com.pism.ezasse.util;

import org.apache.commons.logging.Log;

/**
 * 日志工具类
 *
 * @author PerccyKing
 * @since 2023/12/1 15:07
 */
public class EzasseLogUtil {

    private static final String LOG_CONTEXT = "[ezasse] ";

    private EzasseLogUtil() {
    }

    public static void info(Log log, String msg) {
        if (log.isInfoEnabled()) {
            log.info(LOG_CONTEXT + msg);
        }
    }

    public static void debug(Log log, String msg) {
        if (log.isDebugEnabled()) {
            log.debug(LOG_CONTEXT + msg);
        }
    }

    public static void trace(Log log, String msg) {
        if (log.isTraceEnabled()) {
            log.trace(LOG_CONTEXT + msg);
        }
    }

    public static void error(Log log, String msg) {
        if (log.isErrorEnabled()) {
            log.error(LOG_CONTEXT + msg);
        }
    }

    public static void error(Log log, String msg, Throwable t) {
        if (log.isErrorEnabled()) {
            log.error(LOG_CONTEXT + msg, t);
        }
    }

    public static void warn(Log log, String msg) {
        if (log.isWarnEnabled()) {
            log.warn(LOG_CONTEXT + msg);
        }
    }
}
