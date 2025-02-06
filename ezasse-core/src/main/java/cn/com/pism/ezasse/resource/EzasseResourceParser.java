package cn.com.pism.ezasse.resource;

/**
 * 资源解析器
 *
 * @author PerccyKing
 * @since 24-10-24 22:44
 */
@FunctionalInterface
public interface EzasseResourceParser {

    /**
     * <p>
     * 解析资源位资源数据
     * </p>
     * by perccyking
     *
     * @return {@link EzasseResourceData} 解析后的资源数据
     * @since 25-01-01 13:25
     */
    EzasseResourceData parse(EzasseResource resource);
}
