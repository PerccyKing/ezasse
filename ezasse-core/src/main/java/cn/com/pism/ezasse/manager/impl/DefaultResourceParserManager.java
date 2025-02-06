package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.ResourceParserManager;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的资源解析器工厂实现
 *
 * @author PerccyKing
 * @since 24-10-27 01:04
 */
public class DefaultResourceParserManager implements ResourceParserManager {

    private final Map<Class<? extends EzasseResource>, EzasseResourceParser> parserConstructorMap = new ConcurrentHashMap<>(256);

    @Override
    public EzasseResourceParser getResourceParser(Class<? extends EzasseResource> resourceType) {
        return parserConstructorMap.get(resourceType);
    }


    @Override
    public void registerResourceParser(Class<? extends EzasseResource> resourceClass, EzasseResourceParser ezasseResourceParser) {
        parserConstructorMap.put(resourceClass, ezasseResourceParser);
    }
}
