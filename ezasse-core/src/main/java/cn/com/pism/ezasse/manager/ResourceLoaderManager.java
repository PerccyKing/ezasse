package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.loader.EzasseResourceLoader;

/**
 * 资源加载器管理器
 *
 * @author PerccyKing
 * @since 24-10-24 22:36
 */
public interface ResourceLoaderManager {

    /**
     * <p>
     * 根据资源类获取对应的资源加载器
     * </p>
     * by perccyking
     *
     * @param resourceClass 资源类，用于指定所需资源加载器的类型
     * @return {@link EzasseResourceLoader <T>} 返回一个指定资源类型的EzasseResourceLoader对象
     * @since 24-10-31 00:16
     */
    <T extends EzasseResource> EzasseResourceLoader<T> getResourceLoader(Class<T> resourceClass);

    /**
     * <p>
     * 注册资源加载器
     * </p>
     * by perccyking
     *
     * @param resourceClass  资源类的类型，表示该加载器能够加载的资源类型
     * @param resourceLoader 实现了EzasseResourceLoader接口的资源加载器实例，用于加载指定类型的资源
     * @since 24-10-31 00:17
     */
    <T extends EzasseResource> void registerResourceLoader(Class<T> resourceClass, EzasseResourceLoader<T> resourceLoader);
}
