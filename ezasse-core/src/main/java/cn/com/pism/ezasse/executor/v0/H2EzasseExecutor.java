package cn.com.pism.ezasse.executor.v0;

import cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants;
import cn.com.pism.ezasse.model.EzasseTableInfo;

import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;
import static cn.com.pism.ezasse.util.EzasseUtil.toTableInfo;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 12:32
 */
public class H2EzasseExecutor extends EzasseExecutor {
    /**
     * <p>
     * 获取表信息
     * </p>
     *
     * @param tableName  : 表名
     * @param columnName : 列名
     * @return {@link List < EzasseTableInfo >} 表的基本信息
     * @author PerccyKing
     * @since 2022/04/06 下午 11:22
     */
    @Override
    public List<EzasseTableInfo> getTableInfo(String tableName, String columnName) {
        String getTableInfoSql = "SELECT COLUMN_NAME columnName,DATA_TYPE dataType,CHARACTER_MAXIMUM_LENGTH dataLength,REMARKS columnComment FROM Information_schema.columns WHERE table_Name = ? AND TABLE_SCHEMA=? AND COLUMN_NAME=? ";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(getTableInfoSql, tableName, getDataBaseNameFromDataSource(this.dataSource), columnName);
        return toTableInfo(queryForList);
    }

    /**
     * <p>
     * 获取表信息
     * </p>
     *
     * @param tableName : 表名
     * @return {@link List<EzasseTableInfo>}
     * @author PerccyKing
     * @since 2022/04/09 下午 04:03
     */
    @Override
    public List<EzasseTableInfo> getTableInfo(String tableName) {
        String sql = "SELECT COLUMN_NAME columnName,DATA_TYPE dataType,CHARACTER_MAXIMUM_LENGTH dataLength,REMARKS columnComment FROM Information_schema.columns WHERE table_Name = ? AND TABLE_SCHEMA=? ";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, tableName, getDataBaseNameFromDataSource(this.dataSource));
        return toTableInfo(queryForList);
    }

    /**
     * <p>
     * 获取id {@see cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants}
     * </p>
     *
     * @return {@link String}
     * @author PerccyKing
     * @since 2022/04/11 下午 08:06
     */
    @Override
    public String getId() {
        return EzasseDatabaseTypeConstants.H2;
    }
}
