package cn.com.pism.ezasse.resource;

/**
 * 资源解析器
 *
 * @author PerccyKing
 * @since 24-10-24 22:44
 */
public abstract class EzasseResourceParser {

    /**
     * <p>
     * 资源解析器构造器
     * </p>
     * by perccyking
     *
     * @param resource : 资源
     * @since 25-01-01 13:24
     */
    protected EzasseResourceParser(EzasseResource resource) {
    }

    /**
     * <p>
     * 解析资源位资源数据
     * </p>
     * by perccyking
     *
     * @return {@link EzasseResourceData} 解析后的资源数据
     * @since 25-01-01 13:25
     */
    public abstract EzasseResourceData parse();
}
