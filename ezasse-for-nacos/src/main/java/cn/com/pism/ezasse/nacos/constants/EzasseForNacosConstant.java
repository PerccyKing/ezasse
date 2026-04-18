package cn.com.pism.ezasse.nacos.constants;

/**
 * Nacos 相关的常量类
 *
 * @author PerccyKing
 * @since 25-06-19 20:21
 */
public class EzasseForNacosConstant {
    private EzasseForNacosConstant() {
    }

    /**
     * Nacos 数据源类型标识符
     */
    public static final String NACOS = "NACOS";

    /**
     * 动作标识：覆盖发布
     */
    public static final String ACTION_PUBLISH = "PUBLISH";

    /**
     * 动作标识：合并发布
     */
    public static final String ACTION_MERGE_TO = "MERGE_TO";

    /**
     * Properties 文件后缀
     */
    public static final String EXT_PROPERTIES = ".properties";

    /**
     * YAML 文件后缀
     */
    public static final String EXT_YAML = ".yaml";

    /**
     * YML 文件后缀
     */
    public static final String EXT_YML = ".yml";

    /**
     * 默认 Nacos 请求超时时间（毫秒）
     */
    public static final long DEFAULT_TIMEOUT_MS = 5000L;
}
