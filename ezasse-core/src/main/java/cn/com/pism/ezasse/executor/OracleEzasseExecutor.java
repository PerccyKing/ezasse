package cn.com.pism.ezasse.executor;

import cn.com.pism.ezasse.model.EzasseTableInfo;
import cn.hutool.core.io.FileUtil;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static cn.com.pism.ezasse.constants.EzasseConstants.SQL_EXTENSION;
import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.ORACLE;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 上午 11:47
 */
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
        return jdbcTemplate.queryForList(querySql, EzasseTableInfo.class, tableName, columnName);
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
        return jdbcTemplate.queryForList(SQL, EzasseTableInfo.class, tableName);
    }

    @Override
    public void execute(String sql) {
        try {
            String tempFileName = UUID.randomUUID() + SQL_EXTENSION;
            FileUtil.touch(tempFileName);
            File file = FileUtil.writeBytes(sql.getBytes(), tempFileName);
            ScriptUtils.executeSqlScript(super.getDataSource().getConnection(), new EncodedResource(new PathResource(file.getPath())));
            FileUtil.del(file);
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
