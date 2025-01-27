package cn.com.pism.ezasse.action.jdbc.oracle;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;
import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;
import static cn.com.pism.ezasse.util.EzasseUtil.toTableInfo;

/**
 * @author PerccyKing
 * @since 25-01-27 15:25
 */
public class OracleGetTableInfoAction implements EzasseExecutorAction<GetTableInfoActionParam, List<EzasseTableInfo>> {


    private static final String GET_TABLE_INFO_SQL = "SELECT UTC.COLUMN_NAME columnName,\n" +
            "       UTC.DATA_TYPE   dataType,\n" +
            "       UTC.CHAR_LENGTH characterMaximumLength,\n" +
            "       UCC.COMMENTS    columnComment\n" +
            "FROM USER_TAB_COLS UTC\n" +
            "         LEFT JOIN USER_COL_COMMENTS UCC ON UTC.TABLE_NAME = UCC.TABLE_NAME AND UTC.COLUMN_NAME = UCC.COLUMN_NAME\n" +
            "WHERE UTC.TABLE_NAME = ?\n";

    private static final String COLUMN_FILTER = "AND UTC.COLUMN_NAME = ?";

    private final JdbcTemplate jdbcTemplate;

    public OracleGetTableInfoAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EzasseTableInfo> doAction(GetTableInfoActionParam actionParam) {
        List<Map<String, Object>> queryForList;
        if (StringUtils.isNotBlank(actionParam.getColumnName())) {
            queryForList = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL + COLUMN_FILTER, actionParam.getTableName(), getDataBaseNameFromDataSource(this.jdbcTemplate.getDataSource()), actionParam.getColumnName());
        } else {
            queryForList = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL, actionParam.getTableName(), getDataBaseNameFromDataSource(this.jdbcTemplate.getDataSource()));
        }
        return toTableInfo(queryForList);
    }


    @Override
    public String getId() {
        return GET_TABLE_INFO;
    }
}
