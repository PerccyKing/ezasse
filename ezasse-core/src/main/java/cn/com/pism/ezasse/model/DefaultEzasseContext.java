package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.resource.EzasseResource;
import cn.com.pism.ezasse.resource.EzasseResourceData;
import cn.com.pism.ezasse.resource.factory.EzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.EzasseResourceParserFactory;
import cn.com.pism.ezasse.resource.factory.impl.DefaultEzasseResourceLoaderFactory;
import cn.com.pism.ezasse.resource.factory.impl.DefaultEzasseResourceParserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PerccyKing
 * @since 24-10-22 23:25
 */
public class DefaultEzasseContext implements EzasseContext {

    /**
     * 资源加载器工厂
     */
    private EzasseResourceLoaderFactory loaderFactory;

    /**
     * 资源解析器工厂
     */
    private EzasseResourceParserFactory parserFactory;

    /**
     * 配置
     */
    private EzasseConfig config;

    /**
     * 资源 源数据对应资源实体
     */
    private final Map<Class<? extends EzasseResource>, EzasseResourceData> ezasseResourceDataMap = new ConcurrentHashMap<>(16);

    /**
     * 默认构造器
     */
    public DefaultEzasseContext() {
        // 初始化默认资源加载工厂
        this.loaderFactory = new DefaultEzasseResourceLoaderFactory();
        // 初始化默认资源解析工厂
        this.parserFactory = new DefaultEzasseResourceParserFactory();
    }

    /**
     * 数据源map [id,数据源]
     */
    private final Map<String, EzasseDataSource> dataSourceMap = new ConcurrentHashMap<>(16);

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
     * @param dataSource : 数据源
     * @since 24-10-21 22:35
     */
    @Override
    public void registerDataSource(EzasseDataSource dataSource) {
        dataSourceMap.put(dataSource.getId(), dataSource);
    }

    private final Map<String, EzasseChecker> checkerMap = new ConcurrentHashMap<>(16);

    @Override
    public EzasseChecker getChecker(String checkerId) {
        return checkerMap.get(checkerId);
    }

    @Override
    public void registerChecker(String checkerId, EzasseChecker checker) {
        checkerMap.put(checkerId, checker);
    }

    @Override
    public List<String> getCheckerKeys() {
        return new ArrayList<>(checkerMap.keySet());
    }

    private final Map<String, EzasseExecutor> executorMap = new ConcurrentHashMap<>(16);

    /**
     * 数据源类型，对应的执行器类型
     */
    private final Map<String, Class<? extends EzasseExecutor>> executorClassMap = new ConcurrentHashMap<>(16);

    @Override
    public EzasseExecutor getExecutor(String dataSourceId) {
        return executorMap.computeIfAbsent(dataSourceId, id -> {
            // 获取数据源
            EzasseDataSource dataSource = getDataSource(id);

            // 通过数据源类型获取执行器的类型
            Class<? extends EzasseExecutor> ezasseExecutorClass = executorClassMap.get(dataSource.getType());
            try {
                // 实例化执行器
                return ezasseExecutorClass.getConstructor(EzasseDataSource.class).newInstance(dataSource);
            } catch (Exception e) {
                throw new EzasseException("really?!!");
            }
        });
    }


    @Override
    public void registerExecutor(String dataSourceType, Class<? extends EzasseExecutor> executorClass) {
        executorClassMap.put(dataSourceType, executorClass);
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
