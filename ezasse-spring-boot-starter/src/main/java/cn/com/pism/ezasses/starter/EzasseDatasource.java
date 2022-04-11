package cn.com.pism.ezasses.starter;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/11 上午 12:20
 * @since 0.0.1
 */
public interface EzasseDatasource {

    /**
     * <p>
     * 获取所有的数据源,有多个数据源的时候，需要实现该接口
     * </p>
     *
     * @return {@link Map<String,DataSource>}
     * @author PerccyKing
     * @date 2022/04/11 下午 08:00
     */
    Map<String, DataSource> getDataSource();

    /**
     * <p>
     * 获取master数据源，如果需要重新指定数据源需要实现该接口，默认为jdbctemplate 获取到的数据源
     * </p>
     *
     * @return {@link DataSource}
     * @author PerccyKing
     * @date 2022/04/11 下午 08:01
     */
    DataSource getMaster();

}
