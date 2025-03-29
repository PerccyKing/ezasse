package cn.com.pism.ezasse;

import cn.com.pism.ezasse.context.DefaultEzasseContext;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.manager.ResourceLoaderManager;
import cn.com.pism.ezasse.manager.ResourceParserManager;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;

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

    protected EzasseContext ezasseContext;

    public EzasseContext getContext() {
        return ezasseContext;
    }

    protected AbstractEzasse(Class<? extends EzasseResource> resourceClass) {
        this.resourceClass = resourceClass;
        this.ezasseContext = new DefaultEzasseContext();
        EzasseContextHolder.setEzasseContext(ezasseContext);
    }

    protected AbstractEzasse(Class<? extends EzasseResource> resourceClass,
                             EzasseContext ezasseContext) {
        this.resourceClass = resourceClass;
        this.ezasseContext = ezasseContext;
        EzasseContextHolder.setEzasseContext(ezasseContext);
    }


    public void execute() {
        //加载并解析资源
        loadAndParse();

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

        //获取资源加载器
        ResourceLoaderManager resourceLoaderManager = ezasseContext.resourceLoaderManager();

        //加载资源
        EzasseResource ezasseResource = resourceLoaderManager.getResourceLoader(this.resourceClass).load();

        //获取资源的解析器
        ResourceParserManager resourceParserManager = ezasseContext.resourceParserManager();

        //解析数据
        EzasseResourceData ezasseResourceData = resourceParserManager.getResourceParser(this.resourceClass).parse(ezasseResource);

        //将解析出来的数据放入上下文
        ezasseContext.resourceManager().cacheEzasseResource(this.resourceClass, ezasseResourceData);

    }


    protected void preProcess() {
        //default do nothing
    }

    protected abstract void doExecute();

}
