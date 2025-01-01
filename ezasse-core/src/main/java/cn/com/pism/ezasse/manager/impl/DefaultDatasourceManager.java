package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.DatasourceManager;
import cn.com.pism.ezasse.model.EzasseDataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源管理器默认实现
 * @author PerccyKing
 * @since 25-01-01 11:31
 */
public class DefaultDatasourceManager implements DatasourceManager {

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
}
