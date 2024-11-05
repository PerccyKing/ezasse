package cn.com.pism.ezasse.resource;

/**
 * 资源解析器
 *
 * @author PerccyKing
 * @since 24-10-24 22:44
 */
public abstract class EzasseResourceParser {

    protected EzasseResourceParser(EzasseResource resource) {
    }
    public abstract EzasseResourceData parse();
}
