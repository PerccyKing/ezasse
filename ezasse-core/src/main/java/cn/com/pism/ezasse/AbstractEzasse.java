package cn.com.pism.ezasse;

import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.factory.EzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.EzasseResourceParserFactory;

/**
 * 加载文件
 * 解析为EzasseFile
 * 将file解析为不同的数据源
 * 通过数据源获取校验器
 * 校验通过获取执行器并且执行
 *
 * @author PerccyKing
 * @since 24-10-23 23:16
 */
public abstract class AbstractEzasse {

    protected Class<? extends EzasseResource> resourceClass;

    protected AbstractEzasse(Class<? extends EzasseResource> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public void execute() {
        //加载并解析资源
        loadAndParse();

        //对资源进行校验
        check(EzasseContextHolder.getContext().getEzassea(this.resourceClass));

        //执行动作
        doExecute();
    }

    /**
     * <p>
     * 加载并解析资源
     * </p>
     * by perccyking
     *
     * @since 24-10-29 22:40
     */
    protected void loadAndParse() {
        //前置处理
        preProcess();

        //获取上下文
        EzasseContext context = EzasseContextHolder.getContext();


        //获取资源加载器
        EzasseResourceLoaderFactory resourceLoaderFactory = context.getResourceLoaderFactory();

        //加载资源
        EzasseResource ezasseResource = resourceLoaderFactory.getResourceLoader(this.resourceClass).load();

        //获取资源的解析器
        EzasseResourceParserFactory resourceParserFactory = context.getResourceParserFactory();

        //将解析出来的数据放入上下文
        context.putEzassea(this.resourceClass, resourceParserFactory.getResourceParser(ezasseResource).parse());

    }


    protected void preProcess() {
        //default do nothing
    }


    protected abstract void check(EzasseResourceData ezasseResourceData);

    protected abstract void doExecute();

}
