package cn.com.pism.ezasse.executor;

import cn.com.pism.ezasse.model.EzasseTableInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.util.EzasseUtil.getFromDataSource;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/07 下午 05:57
 * @since 0.0.1
 */
@Slf4j
public class MysqlEzasseExecutor extends EzasseExecutor {

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
    @Override
    public List<EzasseTableInfo> getTableInfo(String tableName, String columnName) {
        String getTableInfoSql = "SELECT COLUMN_NAME columnName,DATA_TYPE dataType,CHARACTER_MAXIMUM_LENGTH characterMaximumLength,COLUMN_COMMENT columnComment FROM Information_schema.columns WHERE table_Name = ? AND TABLE_SCHEMA=? AND COLUMN_NAME=? ";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(getTableInfoSql, tableName, getDataBaseNameFromDataSource(this.dataSource), columnName);
        return JSON.parseArray(JSON.toJSONString(queryForList), EzasseTableInfo.class);
    }

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
    @Override
    public List<EzasseTableInfo> getTableInfo(String tableName) {
        String sql = "SELECT COLUMN_NAME columnName,DATA_TYPE dataType,CHARACTER_MAXIMUM_LENGTH characterMaximumLength,COLUMN_COMMENT columnComment FROM Information_schema.columns WHERE table_Name = ? AND TABLE_SCHEMA=? ";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, tableName, getDataBaseNameFromDataSource(this.dataSource));
        return JSON.parseArray(JSON.toJSONString(queryForList), EzasseTableInfo.class);
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
}
