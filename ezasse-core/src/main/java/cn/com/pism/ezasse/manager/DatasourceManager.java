package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.model.EzasseDataSource;

/**
 * 数据源管理器
 *
 * @author PerccyKing
 * @since 25-01-01 11:29
 */
public interface DatasourceManager {


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

    /**
     * 注册主数据源
     *
     * @param dataSource : 数据源
     */
    void registerMasterDataSource(EzasseDataSource dataSource);

    /**
     * <p>
     * 检查数据源是否为空
     * </p>
     * by perccyking
     *
     * @return true：没有任何数据源
     * @since 25-03-15 21:00
     */
    boolean isEmpty();

}
