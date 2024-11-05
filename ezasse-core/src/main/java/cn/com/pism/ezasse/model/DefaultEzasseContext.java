package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.resource.factory.impl.DefaultEzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.impl.DefaultEzasseResourceParserFactory;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.factory.EzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.EzasseResourceParserFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author PerccyKing
 * @since 24-10-22 23:25
 */
public class DefaultEzasseContext implements EzasseContext {

    private EzasseResourceLoaderFactory loaderFactory;

    private EzasseResourceParserFactory parserFactory;

    private EzasseConfig config;

    public DefaultEzasseContext() {
        this.loaderFactory = new DefaultEzasseResourceLoaderFactory();
        this.parserFactory = new DefaultEzasseResourceParserFactory();
    }

    /**
     * <p>
     * 获取所有数据源
     * </p>
     * by perccyking
     *
     * @return 所有数据源列表
     * @since 24-10-21 22:35
     */
    @Override
    public List<AbstractEzasseDataSource<?>> getDataSources() {
        return Collections.emptyList();
    }

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
    @Override
    public AbstractEzasseDataSource<?> getDataSource(String dataSourceId) {
        return null;
    }

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
    @Override
    public void registerDataSource(String dataSourceId, AbstractEzasseDataSource<?> dataSource) {

    }

    @Override
    public EzasseChecker getChecker(String checkerId) {
        return null;
    }

    @Override
    public void registerChecker(String checkerId, EzasseChecker checker) {

    }

    @Override
    public EzasseExecutor getExecutor(String executorId) {
        return null;
    }

    @Override
    public void registerExecutor(String executorId, EzasseExecutor executor) {

    }

    /**
     * <p>
     * 设置配置
     * </p>
     * by perccyking
     *
     * @param config : ezasse配置
     * @since 24-10-22 00:10
     */
    @Override
    public void setConfig(EzasseConfig config) {
        this.config = config;
    }

    /**
     * <p>
     * 获取ezasseP诶之
     * </p>
     * by perccyking
     *
     * @return {@link EzasseConfig} ezasse配置
     * @since 24-10-22 00:11
     */
    @Override
    public EzasseConfig getConfig() {
        return config;
    }

    @Override
    public EzasseResourceLoaderFactory getResourceLoaderFactory() {
        return this.loaderFactory;
    }

    @Override
    public void registerEzasseResourceLoaderFactory(EzasseResourceLoaderFactory loaderFactory) {
        this.loaderFactory = loaderFactory;
    }

    @Override
    public EzasseResourceParserFactory getResourceParserFactory() {
        return parserFactory;
    }

    @Override
    public void registerEzasseResourceParserFactory(EzasseResourceParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    @Override
    public void putEzassea(Class<? extends EzasseResource> resourceClass, EzasseResourceData ezasseResourceData) {

    }

    @Override
    public EzasseResourceData getEzassea(Class<? extends EzasseResource> resourceClass) {
        return null;
    }
}
