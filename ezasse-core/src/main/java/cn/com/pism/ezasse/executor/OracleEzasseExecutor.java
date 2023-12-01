package cn.com.pism.ezasse.executor;

import cn.com.pism.ezasse.model.EzasseTableInfo;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.ORACLE;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 上午 11:47
 */
@Slf4j
public class OracleEzasseExecutor extends EzasseExecutor {

    private static final String SQL = "SELECT UTC.COLUMN_NAME columnName,\n" +
            "       UTC.DATA_TYPE   dataType,\n" +
            "       UTC.CHAR_LENGTH characterMaximumLength,\n" +
            "       UCC.COMMENTS    columnComment\n" +
            "FROM USER_TAB_COLS UTC\n" +
            "         LEFT JOIN USER_COL_COMMENTS UCC ON UTC.TABLE_NAME = UCC.TABLE_NAME AND UTC.COLUMN_NAME = UCC.COLUMN_NAME\n" +
            "WHERE UTC.TABLE_NAME = ?\n";

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
        String querySql = SQL + "AND UTC.COLUMN_NAME = ?";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(querySql, tableName, columnName);
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
     * @since 2022/04/09 下午 04:03
     */
    @Override
    public List<EzasseTableInfo> getTableInfo(String tableName) {
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(SQL, tableName);
        return JSON.parseArray(JSON.toJSONString(queryForList), EzasseTableInfo.class);
    }

    @Override
    public void execute(String sql) {
        try {
            ByteArrayResource resource = new ByteArrayResource(sql.getBytes());
            ScriptUtils.executeSqlScript(super.getDataSource().getConnection(), resource);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
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
        return ORACLE;
    }
}