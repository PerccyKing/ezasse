package cn.com.pism.ezasse.loader;

import cn.com.pism.ezasse.resource.EzasseResource;

/**
 * 资源加载器
 *
 * @author PerccyKing
 * @since 24-10-24 22:34
 */
public interface EzasseResourceLoader<T extends EzasseResource> {

    /**
     * <p>
     * 加载资源
     * </p>
     * by perccyking
     *
     * @return {@link T} 加载到的资源
     * @since 25-01-18 23:12
     */
    T load();
}
