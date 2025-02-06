package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceParser;

/**
 * 资源解析器管理器
 *
 * @author PerccyKing
 * @since 24-10-24 23:22
 */
public interface ResourceParserManager {

    /**
     * <p>
     * 获取资源解析器
     * </p>
     * by perccyking
     *
     * @param resourceType : 资源类型
     * @return {@link EzasseResourceParser} 资源对应的解析器
     * @since 25-01-01 13:23
     */
    EzasseResourceParser getResourceParser(Class<? extends EzasseResource> resourceType);

    /**
     * <p>
     * 注册一个资源解析器
     * </p>
     * by perccyking
     *
     * @param resourceClass        : 资源类型
     * @param ezasseResourceParser : 资源解析器
     * @since 25-01-01 13:23
     */
    void registerResourceParser(Class<? extends EzasseResource> resourceClass, EzasseResourceParser ezasseResourceParser);
}
