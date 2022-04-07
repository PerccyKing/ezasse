package cn.com.pism.ezasse.database;

import cn.com.pism.ezasse.model.EzasseDataSource;

import java.util.List;
import java.util.Objects;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/04 下午 10:52
 * @since 0.0.1
 */
public interface EzasseExecutor {

    void setDataSource(EzasseDataSource dataSource);

    /**
     * <p>
     * 查询数据，并封装为对象
     * </p>
     *
     * @param sql   : sql
     * @param clazz : 对象类型
     * @return {@link T} 对象
     * @author PerccyKing
     * @date 2022/04/06 下午 08:56
     */
    <T> T queryForObject(String sql, Class<T> clazz);

    /**
     * <p>
     * 查询数据并封装为列表
     * </p>
     *
     * @param clazz : 数据对象类型
     * @param sql   : sql
     * @param args  : 参数
     * @return {@link List<T>} 查询到的数据列表
     * @author PerccyKing
     * @date 2022/04/06 下午 11:19
     */
    <T> List<T> queryForList(Class<T> clazz, String sql, Objects... args);

    /**
     * <p>
     * 获取表信息
     * </p>
     *
     * @param tableName  : 表名
     * @param schema     : 模式
     * @param columnName : 列名
     * @return {@link Objects} 表的基本信息
     * @author PerccyKing
     * @date 2022/04/06 下午 11:22
     */
    Objects getTableInfo(String tableName, String schema, String columnName);

    /**
     * <p>
     * 执行SQL
     * </p>
     *
     * @param sql : 需要执行的SQL
     * @author PerccyKing
     * @date 2022/04/06 下午 11:23
     */
    void execute(String sql);
}
