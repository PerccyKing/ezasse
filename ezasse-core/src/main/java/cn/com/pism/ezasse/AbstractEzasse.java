package cn.com.pism.ezasse;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.manager.CheckerManager;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.manager.ResourceLoaderManager;
import cn.com.pism.ezasse.manager.ResourceParserManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;

import java.util.ServiceLoader;

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

        // 注册校验器
        registerCheckers();

        // 注册执行器
        registerExecutors();

        // 注册执行器动作
        registerExecutorActions();
    }

    /**
     * 注册校验器
     */
    private void registerCheckers() {
        CheckerManager checkerManager = EzasseContextHolder.getContext().checkerManager();
        ServiceLoader<EzasseChecker> checkers = ServiceLoader.load(EzasseChecker.class);
        checkers.forEach(checkerManager::registerChecker);
    }

    /**
     * 注册执行器
     */
    private void registerExecutors() {
        ExecutorManager executorManager = EzasseContextHolder.getContext().executorManager();
        ServiceLoader<EzasseExecutor> executors = ServiceLoader.load(EzasseExecutor.class);
        executors.forEach(executor -> executorManager.registerExecutor(executor.getDataSourceType(), executor));
    }

    /**
     * 注册执行器动作
     */
    private void registerExecutorActions() {
        ServiceLoader<ExecutorActionRegister> executorActionRegisters = ServiceLoader.load(ExecutorActionRegister.class);
        executorActionRegisters.forEach(register -> register.registry(EzasseContextHolder.getContext().executorManager()));
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

        //获取上下文
        EzasseContext context = EzasseContextHolder.getContext();


        //获取资源加载器
        ResourceLoaderManager resourceLoaderManager = context.resourceLoaderManager();

        //加载资源
        EzasseResource ezasseResource = resourceLoaderManager.getResourceLoader(this.resourceClass).load();

        //获取资源的解析器
        ResourceParserManager resourceParserManager = context.resourceParserManger();

        //解析数据
        EzasseResourceData ezasseResourceData = resourceParserManager.getResourceParser(this.resourceClass).parse(ezasseResource);

        //将解析出来的数据放入上下文
        context.resourceManger().cacheEzasseResource(this.resourceClass, ezasseResourceData);

    }


    protected void preProcess() {
        //default do nothing
    }

    protected abstract void doExecute();

}
