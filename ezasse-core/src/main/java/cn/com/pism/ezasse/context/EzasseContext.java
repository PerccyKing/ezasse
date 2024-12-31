package cn.com.pism.ezasse.context;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.EzasseExecutorActionParam;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.resource.factory.EzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.EzasseResourceParserFactory;

import java.util.List;

/**
 * ezasse 上下文
 * <ul>
 *     <li>数据源</li>
 * </ul>
 *
 * @author PerccyKing
 * @since 24-10-21 22:28
 */
public interface EzasseContext {

    //====================>  数据源

    /**
     * <p>
     * 根据数据源id获取数据源
     * </p>
     * by perccyking
     *
     * @param dataSourceId : 数据源id
     * @return 数据源
     * @since 24-10-21 22:35
     */
    EzasseDataSource getDataSource(String dataSourceId);

    /**
     * <p>
     * 注册数据源
     * </p>
     * by perccyking
     *
     * @param dataSource : 数据源
     * @since 24-10-21 22:35
     */
    void registerDataSource(EzasseDataSource dataSource);

    //====================>  校验器

    /**
     * <p>
     * 通过校验器id获取校验器实例
     * </p>
     * by perccyking
     *
     * @param checkerId : 校验器id
     * @return {@link EzasseChecker} 校验器
     * @since 24-12-29 17:47
     */
    EzasseChecker getChecker(String checkerId);

    /**
     * <p>
     * 注册校验器
     * </p>
     * by perccyking
     *
     * @param checkerId : 校验器id
     * @param checker   : 校验器
     * @since 24-12-29 17:47
     */
    void registerChecker(String checkerId, EzasseChecker checker);

    /**
     * <p>
     * 获取所有校验器的校验key
     * </p>
     * by perccyking
     *
     * @return 校验key
     * @since 24-12-29 17:48
     */
    List<String> getCheckerKeys();

    //====================>  执行器

    /**
     * <p>
     * 根据数据源id获取执行器
     * </p>
     * by perccyking
     *
     * @param dataSourceId : 数据源id
     * @return {@link EzasseExecutor} 执行器
     * @since 24-12-29 17:52
     */
    EzasseExecutor getExecutor(String dataSourceId);

    /**
     * <p>
     * 注册执行器
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 数据源类型
     * @param executorClass  : 执行器类型
     * @since 24-12-29 17:52
     */
    void registerExecutor(String dataSourceType, Class<? extends EzasseExecutor> executorClass);

    //====================>  配置

    /**
     * <p>
     * 设置配置
     * </p>
     * by perccyking
     *
     * @param config : ezasse配置
     * @since 24-10-22 00:10
     */
    void setConfig(EzasseConfig config);

    /**
     * <p>
     * 获取ezasse配置
     * </p>
     * by perccyking
     *
     * @return {@link EzasseConfig} ezasse配置
     * @since 24-10-22 00:11
     */
    EzasseConfig getConfig();

    /**
     * <p>
     * 获取资源加载器工厂
     * </p>
     * by perccyking
     *
     * @return {@link EzasseResourceLoaderFactory} 资源加载器工厂
     * @since 25-01-01 00:38
     */
    EzasseResourceLoaderFactory getResourceLoaderFactory();

    /**
     * <p>
     * 注册一个资源加载器工厂
     * </p>
     * by perccyking
     *
     * @param loaderFactory : 资源加载器工厂
     * @since 25-01-01 00:39
     */
    void registerEzasseResourceLoaderFactory(EzasseResourceLoaderFactory loaderFactory);

    /**
     * <p>
     * 获取资源解析器工厂
     * </p>
     * by perccyking
     *
     * @return {@link EzasseResourceParserFactory} 资源解析器工厂
     * @since 25-01-01 00:39
     */
    EzasseResourceParserFactory getResourceParserFactory();

    /**
     * <p>
     * 注册资源解析器工厂
     * </p>
     * by perccyking
     *
     * @param parserFactory : 资源解析器工厂
     * @since 25-01-01 00:39
     */
    void registerEzasseResourceParserFactory(EzasseResourceParserFactory parserFactory);

    /**
     * <p>
     * 缓存资源解析后的数据
     * </p>
     * by perccyking
     *
     * @param resourceClass      : 资源类型
     * @param ezasseResourceData : 解析后的资源数据
     * @since 25-01-01 00:40
     */
    void cacheEzasseResource(Class<? extends EzasseResource> resourceClass, EzasseResourceData ezasseResourceData);

    /**
     * <p>
     * 获取资源解析器解析后的数据
     * </p>
     * by perccyking
     *
     * @param resourceClass : 资源类型
     * @return {@link EzasseResourceData} 解析后的资源数据
     * @since 25-01-01 00:41
     */
    EzasseResourceData getEzasseResource(Class<? extends EzasseResource> resourceClass);

    /**
     * <p>
     * 向执行器注册动作
     * </p>
     * by perccyking
     *
     * @param executorType   : 执行器类型
     * @param executorAction : 动作
     * @since 25-01-01 01:08
     */
    void registerExecutorAction(Class<? extends EzasseExecutor> executorType, EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> executorAction);

    /**
     * <p>
     * 获取已注册的执行器动作
     * </p>
     * by perccyking
     *
     * @param executorType : 执行器类型
     * @return 动作列表
     * @since 25-01-01 01:09
     */
    List<EzasseExecutorAction<? extends EzasseExecutorActionParam, ?>> getExecutorAction(Class<? extends EzasseExecutor> executorType);
}
