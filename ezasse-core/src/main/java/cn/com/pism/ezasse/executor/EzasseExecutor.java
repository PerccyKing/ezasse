package cn.com.pism.ezasse.executor;

import cn.com.pism.ezasse.model.EzasseTableInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static cn.com.pism.ezasse.util.EzasseUtil.getFromDataSource;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/04 下午 10:52
 * @since 0.0.1
 */
@Slf4j
@Data
public abstract class EzasseExecutor {


    protected JdbcTemplate jdbcTemplate;

    protected DataSource dataSource;

    /**
     * <p>
     * 设置数据源
     * </p>
     *
     * @param dataSource : 数据源
     * @author PerccyKing
     * @date 2022/04/07 下午 05:54
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
    public <T> T queryForObject(String sql, Class<T> clazz) {
        return jdbcTemplate.queryForObject(sql, clazz);
    }

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
    public <T> List<T> queryForList(Class<T> clazz, String sql, Objects... args) {
        return jdbcTemplate.queryForList(sql, clazz, (Object[]) args);
    }

    /**
     * <p>
     * 获取表信息
     * </p>
     *
     * @param tableName  : 表名
     * @param columnName : 列名
     * @return {@link List<EzasseTableInfo>} 表的基本信息
     * @author PerccyKing
     * @date 2022/04/06 下午 11:22
     */
    public abstract List<EzasseTableInfo> getTableInfo(String tableName, String columnName);

    /**
     * <p>
     * 获取表信息
     * </p>
     *
     * @param tableName : 表名
     * @return {@link List<EzasseTableInfo>}
     * @author PerccyKing
     * @date 2022/04/09 下午 04:03
     */
    public abstract List<EzasseTableInfo> getTableInfo(String tableName);

    /**
     * <p>
     * 执行SQL
     * </p>
     *
     * @param sql : 需要执行的SQL
     * @author PerccyKing
     * @date 2022/04/06 下午 11:23
     */
    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }


    /**
     * <p>
     * 从databse中获取 数据库名称
     * </p>
     *
     * @param dataSource : datasource
     * @return {@link String} 数据库名称
     * @author PerccyKing
     * @date 2022/04/07 下午 03:49
     */
    protected static String getDataBaseNameFromDataSource(DataSource dataSource) {
        String catalog = getFromDataSource(dataSource, connection -> {
            try {
                return connection.getCatalog();
            } catch (SQLException e) {
                log.error(e.getMessage());
                return "";
            }
        });
        if (StringUtils.isBlank(catalog)) {
            return "";
        }
        return catalog;
    }

    /**
     * <p>
     * 获取id {@see cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants}
     * </p>
     *
     * @return {@link String}
     * @author PerccyKing
     * @date 2022/04/11 下午 08:06
     */
    public abstract String getId();
}
