package cn.com.pism.ezasse;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.loader.EzasseFileResourceLoader;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import cn.com.pism.ezasse.resource.EzasseFileResourceParser;

/**
 * @author PerccyKing
 * @since 24-10-27 00:46
 */
public class FileEzasse extends AbstractEzasse {
    public FileEzasse() {
        super(EzasseFileResource.class);
    }

    @Override
    protected void preProcess() {
        EzasseContext context = EzasseContextHolder.getContext();

        //注册资源加载器
        context
                .getResourceLoaderFactory()
                .registerResourceLoader(EzasseFileResource.class, new EzasseFileResourceLoader());

        //注册资源解析器
        context
                .getResourceParserFactory()
                .registerResourceParser(EzasseFileResource.class, EzasseFileResourceParser.class);
    }

    @Override
    protected void check(EzasseResourceData ezasseResourceData) {
        //由 parser 解析为对象
        //对文件中的校验行进行检查，并保留校验通过的sql
    }

    @Override
    protected void doExecute() {
        EzasseResourceData ezasseResourceData = EzasseContextHolder.getContext().getEzassea(EzasseFileResource.class);
        //
    }
}
