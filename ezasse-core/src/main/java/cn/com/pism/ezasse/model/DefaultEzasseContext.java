package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.resource.factory.EzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.EzasseResourceParserFactory;
import cn.com.pism.ezasse.resource.factory.impl.DefaultEzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.impl.DefaultEzasseResourceParserFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PerccyKing
 * @since 24-10-22 23:25
 */
public class DefaultEzasseContext implements EzasseContext {

    private EzasseResourceLoaderFactory loaderFactory;

    private EzasseResourceParserFactory parserFactory;

    private EzasseConfig config;

    private final Map<Class<? extends EzasseResource>, EzasseResourceData> ezasseResourceDataMap = new ConcurrentHashMap<>(16);

    public DefaultEzasseContext() {
        this.loaderFactory = new DefaultEzasseResourceLoaderFactory();
        this.parserFactory = new DefaultEzasseResourceParserFactory();
    }

    private final Map<String, EzasseDataSource> dataSourceMap = new ConcurrentHashMap<>(16);

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
    public List<EzasseDataSource> getDataSources() {
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
    public EzasseDataSource getDataSource(String dataSourceId) {
        return dataSourceMap.get(dataSourceId);
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
    public void registerDataSource(String dataSourceId, EzasseDataSource dataSource) {
        dataSourceMap.put(dataSourceId, dataSource);
    }

    @Override
    public EzasseChecker getChecker(String checkerId) {
        return new ExecChecker();
    }

    @Override
    public void registerChecker(String checkerId, EzasseChecker checker) {

    }

    @Override
    public List<String> getCheckerKeys() {
        return Arrays.asList("EXEC", "TABLE", "ADD");
    }

    @Override
    public EzasseExecutor getExecutor(EzasseDataSource dataSource) {
        return new MysqlEzasseExecutor(dataSource);
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
    public void putEzasseResource(Class<? extends EzasseResource> resourceClass, EzasseResourceData ezasseResourceData) {
        ezasseResourceDataMap.put(resourceClass, ezasseResourceData);
    }

    @Override
    public EzasseResourceData getEzasseResource(Class<? extends EzasseResource> resourceClass) {
        return ezasseResourceDataMap.get(resourceClass);
    }
}
