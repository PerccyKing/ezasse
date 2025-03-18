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

    public static final String REGX_POINT = "\\.";

    public static final int NUM2 = 2;
    public static final int NUM3 = 3;

    /**
     * 校验行正则
     */
    public static final String CHECK_LINE_PATTERN = "^%s\\s*(%s)((?:\\.[a-zA-Z0-9_]+)*)\\((.*?)\\);?$";

    /**
     * master 数据源标记
     */
    public static final String MASTER = "MASTER";


    public static final String COLUMN_NAME = "columnName";

    public static final String DATA_TYPE = "dataType";

    public static final String DATA_LENGTH = "dataLength";

    public static final String COLUMN_COMMENT = "columnComment";

}
