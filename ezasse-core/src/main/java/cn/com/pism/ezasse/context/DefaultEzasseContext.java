package cn.com.pism.ezasse.context;

import cn.com.pism.ezasse.manager.*;
import cn.com.pism.ezasse.manager.impl.*;

/**
 * @author PerccyKing
 * @since 24-10-22 23:25
 */
public class DefaultEzasseContext implements EzasseContext {

    /**
     * 数据源管理器
     */
    private DatasourceManager datasourceManager;

    /**
     * 校验器管理器
     */
    private CheckerManager checkerManager;

    /**
     * 执行器管理器
     */
    private ExecutorManager executorManager;

    /**
     * 资源加载器工厂
     */
    private ResourceLoaderManager resourceLoaderManager;

    /**
     * 资源解析器工厂
     */
    private ResourceParserManager resourceParserManager;

    /**
     * 配置管理器
     */
    private ConfigManager configManager;

    /**
     * 资源管理器
     */
    private ResourceManager resourceManager;

    /**
     * 默认构造器
     */
    public DefaultEzasseContext() {
        this.datasourceManager = new DefaultDatasourceManager();
        this.checkerManager = new DefaultCheckerManager();
        this.executorManager = new DefaultExecutorManager();
        this.configManager = new DefaultConfigManager();
        this.resourceLoaderManager = new DefaultResourceLoaderManager();
        this.resourceParserManager = new DefaultResourceParserManager();
        this.resourceManager = new DefaultResourceManager();

        initAfter();

    }

    public DefaultEzasseContext(DatasourceManager datasourceManager,
                                CheckerManager checkerManager,
                                ExecutorManager executorManager,
                                ResourceLoaderManager resourceLoaderManager,
                                ResourceParserManager resourceParserManager,
                                ConfigManager configManager,
                                ResourceManager resourceManager) {
        this.datasourceManager = datasourceManager;
        this.checkerManager = checkerManager;
        this.executorManager = executorManager;
        this.resourceLoaderManager = resourceLoaderManager;
        this.resourceParserManager = resourceParserManager;
        this.configManager = configManager;
        this.resourceManager = resourceManager;

        initAfter();
    }

    private void initAfter() {
        EzasseContextHolder.setEzasseContext(this);
        this.checkerManager.registerCheckers();
        this.executorManager.registerExecutors();
        this.executorManager.registerExecutorActions();
    }

    /**
     * <p>
     * 数据源管理器
     * </p>
     * by perccyking
     *
     * @return {@link DatasourceManager} 数据源管理器
     * @since 25-01-01 11:33
     */
    @Override
    public DatasourceManager datasourceManager() {
        return this.datasourceManager;
    }

    /**
     * <p>
     * 添加数据源管理器
     * </p>
     * by perccyking
     *
     * @param datasourceManager : 数据源管理器
     * @since 25-01-01 11:41
     */
    @Override
    public void datasourceManager(DatasourceManager datasourceManager) {
        this.datasourceManager = datasourceManager;
    }

    /**
     * <p>
     * 获取校验器管理器
     * </p>
     * by perccyking
     *
     * @return {@link CheckerManager} 校验器管理器
     * @since 25-01-01 12:36
     */
    @Override
    public CheckerManager checkerManager() {
        return this.checkerManager;
    }

    /**
     * <p>
     * 添加校验器管理器
     * </p>
     * by perccyking
     *
     * @param checkerManager :校验器管理器
     * @since 25-01-01 12:36
     */
    @Override
    public void checkerManager(CheckerManager checkerManager) {
        this.checkerManager = checkerManager;
    }

    /**
     * <p>
     * 获取执行器管理器
     * </p>
     * by perccyking
     *
     * @return {@link ExecutorManager} 执行器管理器
     * @since 25-01-01 12:55
     */
    @Override
    public ExecutorManager executorManager() {
        return this.executorManager;
    }

    /**
     * <p>
     * 添加执行器管理器
     * </p>
     * by perccyking
     *
     * @param executorManager : 执行器管理器
     * @since 25-01-01 12:56
     */
    @Override
    public void executorManager(ExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    /**
     * <p>
     * 获取配置管理器
     * </p>
     * by perccyking
     *
     * @return {@link ConfigManager} 配置管理器
     * @since 25-01-01 13:04
     */
    @Override
    public ConfigManager configManager() {
        return this.configManager;
    }

    /**
     * <p>
     * 添加配置管理器
     * </p>
     * by perccyking
     *
     * @param configManager : 配置管理器
     * @since 25-01-01 13:04
     */
    @Override
    public void configManager(ConfigManager configManager) {
        this.configManager = configManager;
    }


    @Override
    public ResourceLoaderManager resourceLoaderManager() {
        return this.resourceLoaderManager;
    }

    @Override
    public void resourceLoaderManager(ResourceLoaderManager resourceLoaderManager) {
        this.resourceLoaderManager = resourceLoaderManager;
    }

    @Override
    public ResourceParserManager resourceParserManager() {
        return resourceParserManager;
    }

    @Override
    public void resourceParserManager(ResourceParserManager resourceParserManager) {
        this.resourceParserManager = resourceParserManager;
    }

    /**
     * <p>
     * 获取资源管理器
     * </p>
     * by perccyking
     *
     * @return {@link ResourceManager} 资源管理器
     * @since 25-01-01 13:36
     */
    @Override
    public ResourceManager resourceManager() {
        return this.resourceManager;
    }

    /**
     * <p>
     * 添加资源管理器
     * </p>
     * by perccyking
     *
     * @param resourceManager : 资源管理器
     * @since 25-01-01 13:37
     */
    @Override
    public void resourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

}
