package cn.com.pism.ezasse;

import cn.com.pism.ezasse.checker.AddFieldChecker;
import cn.com.pism.ezasse.checker.ExecChecker;
import cn.com.pism.ezasse.checker.TableChecker;
import cn.com.pism.ezasse.checker.change.ChangeFieldCommentChecker;
import cn.com.pism.ezasse.checker.change.ChangeFieldLengthChecker;
import cn.com.pism.ezasse.checker.change.ChangeFieldNameChecker;
import cn.com.pism.ezasse.checker.change.ChangeFieldTypeChecker;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.executor.jdbc.H2EzasseExecutor;
import cn.com.pism.ezasse.executor.jdbc.MariaDbEzasseExecutor;
import cn.com.pism.ezasse.executor.jdbc.MysqlEzasseExecutor;
import cn.com.pism.ezasse.executor.jdbc.OracleEzasseExecutor;
import cn.com.pism.ezasse.manager.CheckerManager;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.manager.ResourceLoaderManager;
import cn.com.pism.ezasse.manager.ResourceParserManager;
import cn.com.pism.ezasse.resource.EzasseResource;

import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.*;

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

        //注册校验器
        registerCheckers();

        // 注册执行器
        registerExecutors();
    }

    /**
     * 注册校验器
     */
    private void registerCheckers() {
        CheckerManager checkerManager = EzasseContextHolder.getContext().checkerManager();
        checkerManager.registerChecker(new ExecChecker());
        checkerManager.registerChecker(new TableChecker());
        checkerManager.registerChecker(new AddFieldChecker());
        checkerManager.registerChecker(new ChangeFieldCommentChecker());
        checkerManager.registerChecker(new ChangeFieldLengthChecker());
        checkerManager.registerChecker(new ChangeFieldNameChecker());
        checkerManager.registerChecker(new ChangeFieldTypeChecker());
    }

    /**
     * 注册执行器
     */
    private void registerExecutors() {
        ExecutorManager executorManager = EzasseContextHolder.getContext().executorManager();
        executorManager.registerExecutor(MYSQL, MysqlEzasseExecutor.class);
        executorManager.registerExecutor(MARIADB, MariaDbEzasseExecutor.class);
        executorManager.registerExecutor(ORACLE, OracleEzasseExecutor.class);
        executorManager.registerExecutor(H2, H2EzasseExecutor.class);
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

        //将解析出来的数据放入上下文
        context.resourceManger().cacheEzasseResource(this.resourceClass, resourceParserManager.getResourceParser(ezasseResource).parse());

    }


    protected void preProcess() {
        //default do nothing
    }

    protected abstract void doExecute();

}
