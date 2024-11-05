package cn.com.pism.ezasse.resource;

/**
 * 资源加载器
 *
 * @author PerccyKing
 * @since 24-10-24 22:34
 */
public interface EzasseResourceLoader<T extends EzasseResource> {

    T load();
}
