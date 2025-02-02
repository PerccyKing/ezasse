package cn.com.pism.ezasse.action.jdbc.oracle;

import cn.com.pism.ezasse.action.jdbc.JdbcGetTableInfoAction;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.util.EzasseUtil.toTableInfo;

/**
 * @author PerccyKing
 * @since 25-01-27 15:25
 */
public class OracleGetTableInfoAction extends JdbcGetTableInfoAction {

    private static final String GET_TABLE_INFO_SQL = "SELECT UTC.COLUMN_NAME columnName,\n" +
            "       UTC.DATA_TYPE   dataType,\n" +
            "       UTC.CHAR_LENGTH dataLength,\n" +
            "       UCC.COMMENTS    columnComment\n" +
            "FROM USER_TAB_COLS UTC\n" +
            "         LEFT JOIN USER_COL_COMMENTS UCC ON UTC.TABLE_NAME = UCC.TABLE_NAME AND UTC.COLUMN_NAME = UCC.COLUMN_NAME\n" +
            "WHERE UTC.TABLE_NAME = ?\n";

    private static final String COLUMN_NAME_FILTER = "AND UTC.COLUMN_NAME = ?";

    private final JdbcTemplate jdbcTemplate;

    public OracleGetTableInfoAction(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EzasseTableInfo> doAction(GetTableInfoActionParam actionParam) {
        List<Map<String, Object>> tableInfos;
        if (StringUtils.isNotBlank(actionParam.getColumnName())) {
            tableInfos = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL + COLUMN_NAME_FILTER, actionParam.getTableName(), actionParam.getColumnName());
        } else {
            tableInfos = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL, actionParam.getTableName());
        }
        return toTableInfo(tableInfos);
    }
}
