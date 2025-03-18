package cn.com.pism.ezasse.util;

import org.apache.commons.logging.Log;

/**
 * 日志工具类
 *
 * @author PerccyKing
 * @since 2023/12/1 15:07
 */
public class EzasseLogUtil {

    private EzasseLogUtil() {
    }

    public static void info(Log log, Object msg) {
        if (log.isInfoEnabled()) {
            log.info(msg);
        }
    }

    public static void debug(Log log, Object msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    public static void error(Log log, String msg) {
        if (log.isErrorEnabled()) {
            log.error(msg);
        }
    }

    public static void warn(Log log, String msg) {
        if (log.isWarnEnabled()) {
            log.warn(msg);
        }
    }
}
