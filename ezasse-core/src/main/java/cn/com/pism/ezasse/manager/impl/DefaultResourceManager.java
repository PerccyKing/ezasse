package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.ResourceManager;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源管理器默认实现
 *
 * @author PerccyKing
 * @since 25-01-01 13:32
 */
public class DefaultResourceManager implements ResourceManager {

    /**
     * 资源 源数据对应资源实体
     */
    private final Map<Class<? extends EzasseResource>, EzasseResourceData> ezasseResourceDataMap = new ConcurrentHashMap<>(16);


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
    @Override
    public void cacheEzasseResource(Class<? extends EzasseResource> resourceClass, EzasseResourceData ezasseResourceData) {
        ezasseResourceDataMap.put(resourceClass, ezasseResourceData);
    }

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
    @Override
    public EzasseResourceData getEzasseResource(Class<? extends EzasseResource> resourceClass) {
        return ezasseResourceDataMap.get(resourceClass);
    }
}
