package cn.com.pism.ezasse.resource.factory.impl;

import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceLoader;
import cn.com.pism.ezasse.resource.factory.EzasseResourceLoaderFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PerccyKing
 * @since 24-10-27 00:59
 */
public class DefaultEzasseResourceLoaderFactory implements EzasseResourceLoaderFactory {

    private final Map<Class<? extends EzasseResource>, EzasseResourceLoader<? extends EzasseResource>> loaders = new ConcurrentHashMap<>(256);

    @Override
    public <T extends EzasseResource> EzasseResourceLoader<T> getResourceLoader(Class<T> resourceClass) {
        EzasseResourceLoader<? extends EzasseResource> loader = loaders.get(resourceClass);
        if (loader == null) {
            throw new IllegalArgumentException("No resource loader registered for class: " + resourceClass.getName());
        }
        return createTypedLoader(loader, resourceClass);
    }

    @Override
    public <T extends EzasseResource> void registerResourceLoader(Class<T> resourceClass, EzasseResourceLoader<T> resourceLoader) {

        if (resourceClass == null) {
            throw new IllegalArgumentException("Resource class cannot be null.");
        }
        if (resourceLoader == null) {
            throw new IllegalArgumentException("Resource loader cannot be null.");
        }

        loaders.put(resourceClass, resourceLoader);
    }

    private <T extends EzasseResource> EzasseResourceLoader<T> createTypedLoader(EzasseResourceLoader<? extends EzasseResource> loader, Class<T> resourceClass) {
        return new TypedEzasseResourceLoader<>(loader, resourceClass);
    }

    private static class TypedEzasseResourceLoader<T extends EzasseResource> implements EzasseResourceLoader<T> {

        private final EzasseResourceLoader<? extends EzasseResource> delegate;
        private final Class<T> resourceClass;

        public TypedEzasseResourceLoader(EzasseResourceLoader<? extends EzasseResource> delegate, Class<T> resourceClass) {
            this.delegate = delegate;
            this.resourceClass = resourceClass;
        }

        @Override
        public T load() {
            EzasseResource resource = delegate.load();
            if (resourceClass.isInstance(resource)) {
                return resourceClass.cast(resource);
            } else {
                throw new ClassCastException("Loader returned an incorrect type: " + resource.getClass().getName() + " is not a " + resourceClass.getName());
            }
        }
    }
}

