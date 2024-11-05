package cn.com.pism.ezasse.resource.factory;

import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceParser;

/**
 * @author PerccyKing
 * @since 24-10-24 23:22
 */
public interface EzasseResourceParserFactory {

    EzasseResourceParser getResourceParser(EzasseResource resource);

    <I extends EzasseResource, P extends EzasseResourceParser> void registerResourceParser(Class<I> resourceClass, Class<P> resourceParserClass);
}
