package cn.com.pism.ezasse.context;

import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.resource.EzasseResource;
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
     * 获取所有数据源
     * </p>
     * by perccyking
     *
     * @return 所有数据源列表
     * @since 24-10-21 22:35
     */
    List<EzasseDataSource> getDataSources();

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
     * @param dataSourceId : 数据源id
     * @param dataSource   : 数据源
     * @since 24-10-21 22:35
     */
    void registerDataSource(String dataSourceId, EzasseDataSource dataSource);

    //====================>  校验器

    EzasseChecker getChecker(String checkerId);

    void registerChecker(String checkerId, EzasseChecker checker);

    List<String> getCheckerKeys();

    //====================>  执行器

    EzasseExecutor getExecutor(EzasseDataSource dataSource);

    void registerExecutor(String executorId, EzasseExecutor executor);

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
