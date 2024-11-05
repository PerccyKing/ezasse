package cn.com.pism.ezasse.resource.factory.impl;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceParser;
import cn.com.pism.ezasse.resource.factory.EzasseResourceParserFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的资源解析器工厂实现
 *
 * @author PerccyKing
 * @since 24-10-27 01:04
 */
public class DefaultEzasseResourceParserFactory implements EzasseResourceParserFactory {

    private final Map<Class<? extends EzasseResource>, Constructor<? extends EzasseResourceParser>> parserConstructorMap = new ConcurrentHashMap<>(256);

    @Override
    public EzasseResourceParser getResourceParser(EzasseResource resource) {
        Class<? extends EzasseResource> resourceClass = resource.getClass();
        Constructor<? extends EzasseResourceParser> constructor = parserConstructorMap.get(resourceClass);
        if (constructor == null) {
            throw new IllegalArgumentException("No resource parser registered for class: " + resourceClass.getName());
        }
        try {
            return constructor.newInstance(resource);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EzasseException("Failed to create instance of parser class: " + constructor.getDeclaringClass().getName(), e);
        }
    }

    @Override
    public <I extends EzasseResource, P extends EzasseResourceParser> void registerResourceParser(Class<I> resourceClass, Class<P> resourceParserClass) {
        if (resourceClass == null) {
            throw new IllegalArgumentException("Resource class cannot be null.");
        }
        if (resourceParserClass == null) {
            throw new IllegalArgumentException("Resource parser class cannot be null.");
        }
        try {
            Constructor<P> constructor = resourceParserClass.getConstructor(EzasseResource.class);
            parserConstructorMap.put(resourceClass, constructor);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No suitable constructor found for parser class: " + resourceParserClass.getName(), e);
        }
    }

}
