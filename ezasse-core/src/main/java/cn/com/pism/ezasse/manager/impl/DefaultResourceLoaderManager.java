package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.loader.EzasseResourceLoader;
import cn.com.pism.ezasse.manager.ResourceLoaderManager;
import cn.com.pism.ezasse.resource.EzasseResource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源加载器管理器
 *
 * @author PerccyKing
 * @since 24-10-27 00:59
 */
public class DefaultResourceLoaderManager implements ResourceLoaderManager {

    private final Map<Class<? extends EzasseResource>, EzasseResourceLoader<? extends EzasseResource>> loaders = new ConcurrentHashMap<>(256);

    @Override
    public EzasseResourceLoader<? extends EzasseResource> getResourceLoader(Class<? extends EzasseResource> resourceClass) {
        EzasseResourceLoader<? extends EzasseResource> loader = loaders.get(resourceClass);
        if (loader == null) {
            throw new IllegalArgumentException("No resource loader registered for class: " + resourceClass.getName());
        }
        return loader;
    }

    @Override
    public void registerResourceLoader(Class<? extends EzasseResource> resourceClass, EzasseResourceLoader<? extends EzasseResource> resourceLoader) {

        if (resourceClass == null) {
            throw new IllegalArgumentException("Resource class cannot be null.");
        }
        if (resourceLoader == null) {
            throw new IllegalArgumentException("Resource loader cannot be null.");
        }

        loaders.put(resourceClass, resourceLoader);
    }

}

