package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;

/**
 * 资源管理器
 *
 * @author PerccyKing
 * @since 25-01-01 13:32
 */
public interface ResourceManager {


    /**
     * <p>
     * 缓存资源解析后的数据
     * </p>
     * by perccyking
     *
     * @param resourceClass      : 资源类型
     * @param ezasseResourceData : 解析后的资源数据
     * @since 25-01-01 00:40
     */
    void cacheEzasseResource(Class<? extends EzasseResource> resourceClass, EzasseResourceData ezasseResourceData);

    /**
     * <p>
     * 获取资源解析器解析后的数据
     * </p>
     * by perccyking
     *
     * @param resourceClass : 资源类型
     * @return {@link EzasseResourceData} 解析后的资源数据
     * @since 25-01-01 00:41
     */
    EzasseResourceData getEzasseResource(Class<? extends EzasseResource> resourceClass);

}
