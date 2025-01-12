package cn.com.pism.ezasse.context;

import cn.com.pism.ezasse.manager.*;

/**
 * ezasse 上下文
 * <ul>
 *     <li>数据源</li>
 *     <li>校验器</li>
 *     <li>执行器</li>
 *     <li>配置</li>
 *     <li>资源加载器</li>
 *     <li>资源解析器</li>
 *     <li></li>
 * </ul>
 *
 * @author PerccyKing
 * @since 24-10-21 22:28
 */
public interface EzasseContext {

    //====================>  数据源

    /**
     * <p>
     * 数据源管理器
     * </p>
     * by perccyking
     *
     * @return {@link DatasourceManager} 数据源管理器
     * @since 25-01-01 11:33
     */
    DatasourceManager datasourceManager();

    /**
     * <p>
     * 添加数据源管理器
     * </p>
     * by perccyking
     *
     * @param datasourceManager : 数据源管理器
     * @since 25-01-01 11:41
     */
    void datasourceManager(DatasourceManager datasourceManager);

    //====================>  校验器

    /**
     * <p>
     * 获取校验器管理器
     * </p>
     * by perccyking
     *
     * @return {@link CheckerManager} 校验器管理器
     * @since 25-01-01 12:36
     */
    CheckerManager checkerManager();

    /**
     * <p>
     * 添加校验器管理器
     * </p>
     * by perccyking
     *
     * @param checkManager :校验器管理器
     * @since 25-01-01 12:36
     */
    void checkerManager(CheckerManager checkManager);

    //====================>  执行器

    /**
     * <p>
     * 获取执行器管理器
     * </p>
     * by perccyking
     *
     * @return {@link ExecutorManager} 执行器管理器
     * @since 25-01-01 12:55
     */
    ExecutorManager executorManager();

    /**
     * <p>
     * 添加执行器管理器
     * </p>
     * by perccyking
     *
     * @param executorManager : 执行器管理器
     * @since 25-01-01 12:56
     */
    void executorManager(ExecutorManager executorManager);

    //====================>  配置

    /**
     * <p>
     * 获取配置管理器
     * </p>
     * by perccyking
     *
     * @return {@link ConfigManager} 配置管理器
     * @since 25-01-01 13:04
     */
    ConfigManager configManger();

    /**
     * <p>
     * 添加配置管理器
     * </p>
     * by perccyking
     *
     * @param configManager : 配置管理器
     * @since 25-01-01 13:04
     */
    void configManager(ConfigManager configManager);

    /**
     * <p>
     * 获取资源加载器管理器
     * </p>
     * by perccyking
     *
     * @return {@link ResourceLoaderManager} 资源加载器管理器
     * @since 25-01-01 00:38
     */
    ResourceLoaderManager resourceLoaderManager();

    /**
     * <p>
     * 注册一个资源加载器管理器
     * </p>
     * by perccyking
     *
     * @param resourceLoaderManager : 资源加载器管理器
     * @since 25-01-01 00:39
     */
    void resourceLoaderManager(ResourceLoaderManager resourceLoaderManager);

    /**
     * <p>
     * 获取资源解析器管理器
     * </p>
     * by perccyking
     *
     * @return {@link ResourceParserManager} 资源解析器管理器
     * @since 25-01-01 00:39
     */
    ResourceParserManager resourceParserManger();

    /**
     * <p>
     * 注册资源解析器管理器
     * </p>
     * by perccyking
     *
     * @param resourceParserManager : 资源解析器管理器
     * @since 25-01-01 00:39
     */
    void resourceParserManger(ResourceParserManager resourceParserManager);

    /**
     * <p>
     * 获取资源管理器
     * </p>
     * by perccyking
     *
     * @return {@link ResourceManager} 资源管理器
     * @since 25-01-01 13:36
     */
    ResourceManager resourceManger();

    /**
     * <p>
     * 添加资源管理器
     * </p>
     * by perccyking
     *
     * @param resourceManger : 资源管理器
     * @since 25-01-01 13:37
     */
    void resourceManger(ResourceManager resourceManger);
}
