package cn.com.pism.ezasse.constants;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/04 下午 11:11
 */
public class EzasseConstants {

    private EzasseConstants() {
    }

    public static final String BACK_SLASH = "/";

    public static final String CLASSPATH_PREFIX = "classpath:";

    public static final String SQL_EXTENSION = ".sql";

    public static final String MINUS_SIGN = "-";

    public static final String LINE_COMMENT = "--";

    public static final String LEFT_BRACKET = "(";

    public static final String RIGHT_BRACKET = ")";

    public static final String REGX_POINT = "\\.";

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;

    /**
     * 开始界定符
     */
    public static final String START_DEFINER = "-- [";

    /**
     * 结束界定符
     */
    public static final String END_DEFINER = "-- ]";

    /**
     * 校验行正则
     */
    public static final String CHECK_LINE_PATTERN = "^%s\\s*(%s)((?:\\.[a-zA-Z0-9_]+)*)\\((.*?)\\);?$";
}
