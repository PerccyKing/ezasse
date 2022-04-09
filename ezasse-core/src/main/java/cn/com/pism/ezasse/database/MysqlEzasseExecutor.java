package cn.com.pism.ezasse.database;

import cn.com.pism.ezasse.model.EzasseTableInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        return jdbcTemplate.queryForList(getTableInfoSql, EzasseTableInfo.class, tableName, getSchema(), columnName);
    }


    private String getSchema() {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
            return connection.getCatalog();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return "";
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
