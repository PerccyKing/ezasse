package cn.com.pism.ezasse.executor;

import cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants;
import cn.com.pism.ezasse.model.EzasseTableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 07:16
 */
public class HsqlDbExecutor extends EzasseExecutor {
    private static final String SQL = "select ISC.COLUMN_NAME              columnName,\n" +
            "       ISC.DATA_TYPE                dataType,\n" +
            "       ISC.CHARACTER_MAXIMUM_LENGTH characterMaximumLength,\n" +
            "       ISSC.REMARKS                 columnComment\n" +
            "from INFORMATION_SCHEMA.COLUMNS ISC\n" +
            "         left join INFORMATION_SCHEMA.SYSTEM_COLUMNS ISSC\n" +
            "                   on ISC.TABLE_NAME = ISSC.TABLE_NAME and ISC.TABLE_SCHEMA = ISSC.TABLE_SCHEM and\n" +
            "                      ISC.COLUMN_NAME = ISSC.COLUMN_NAME\n" +
            "where ISC.TABLE_NAME = ?\n" +
            "  and ISC.TABLE_SCHEMA = ?\n";

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
        String querySql = SQL + "AND ISC.COLUMN_NAME = ?";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(querySql, tableName, columnName);
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
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(SQL, tableName);
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
        return EzasseDatabaseTypeConstants.HSQLDB;
    }
}
