package cn.com.pism.ezasse.context;

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

    EzasseResourceLoaderFactory getResourceLoaderFactory();

    void registerEzasseResourceLoaderFactory(EzasseResourceLoaderFactory loaderFactory);

    EzasseResourceParserFactory getResourceParserFactory();

    void registerEzasseResourceParserFactory(EzasseResourceParserFactory parserFactory);

    void putEzasseResource(Class<? extends EzasseResource> resourceClass, EzasseResourceData ezasseResourceData);

    EzasseResourceData getEzasseResource(Class<? extends EzasseResource> resourceClass);

}
