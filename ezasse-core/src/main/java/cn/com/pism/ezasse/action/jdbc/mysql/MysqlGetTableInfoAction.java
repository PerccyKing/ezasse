package cn.com.pism.ezasse.action.jdbc.mysql;

import cn.com.pism.ezasse.action.jdbc.GetTableInfoAction;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;

/**
 * @author PerccyKing
 * @since 24-12-28 11:17
 */
public class MysqlGetTableInfoAction extends GetTableInfoAction {

    private static final String GET_TABLE_INFO_SQL =
            "SELECT COLUMN_NAME columnName," +
                    "DATA_TYPE dataType," +
                    "CHARACTER_MAXIMUM_LENGTH characterMaximumLength," +
                    "COLUMN_COMMENT columnComment " +
                    "FROM Information_schema.columns WHERE TABLE_NAME = ? AND TABLE_SCHEMA=? ";

    private static final String COLUMN_FILTER = "AND COLUMN_NAME = ?";

    public MysqlGetTableInfoAction(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableInfoSql() {
        return GET_TABLE_INFO_SQL;
    }

    @Override
    protected String getFieldFilterSql() {
        return COLUMN_FILTER;
    }

    @Override
    public String getId() {
        return GET_TABLE_INFO;
    }
}
