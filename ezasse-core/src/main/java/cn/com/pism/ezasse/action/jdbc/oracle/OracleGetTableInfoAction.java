package cn.com.pism.ezasse.action.jdbc.oracle;

import cn.com.pism.ezasse.action.jdbc.GetTableInfoAction;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;

/**
 * @author PerccyKing
 * @since 25-01-27 15:25
 */
public class OracleGetTableInfoAction extends GetTableInfoAction {


    private static final String GET_TABLE_INFO_SQL = "SELECT UTC.COLUMN_NAME columnName,\n" +
            "       UTC.DATA_TYPE   dataType,\n" +
            "       UTC.CHAR_LENGTH characterMaximumLength,\n" +
            "       UCC.COMMENTS    columnComment\n" +
            "FROM USER_TAB_COLS UTC\n" +
            "         LEFT JOIN USER_COL_COMMENTS UCC ON UTC.TABLE_NAME = UCC.TABLE_NAME AND UTC.COLUMN_NAME = UCC.COLUMN_NAME\n" +
            "WHERE UTC.TABLE_NAME = ?\n";

    private static final String COLUMN_FILTER = "AND UTC.COLUMN_NAME = ?";

    public OracleGetTableInfoAction(JdbcTemplate jdbcTemplate) {
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
