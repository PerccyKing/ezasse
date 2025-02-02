package cn.com.pism.ezasse.action.jdbc.hsqldb;

import cn.com.pism.ezasse.action.jdbc.JdbcGetTableInfoAction;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;
import static cn.com.pism.ezasse.util.EzasseUtil.toTableInfo;

/**
 * @author PerccyKing
 * @since 25-02-02 22:45
 */
public class HsqlDbGetTableInfoAction extends JdbcGetTableInfoAction {

    private static final String GET_TABLE_INFO_SQL = "select ISC.COLUMN_NAME              columnName,\n" +
            "       ISC.DATA_TYPE                dataType,\n" +
            "       ISC.CHARACTER_MAXIMUM_LENGTH dataLength,\n" +
            "       ISSC.REMARKS                 columnComment\n" +
            "from INFORMATION_SCHEMA.COLUMNS ISC\n" +
            "         left join INFORMATION_SCHEMA.SYSTEM_COLUMNS ISSC\n" +
            "                   on ISC.TABLE_NAME = ISSC.TABLE_NAME and ISC.TABLE_SCHEMA = ISSC.TABLE_SCHEM and\n" +
            "                      ISC.COLUMN_NAME = ISSC.COLUMN_NAME\n" +
            "where ISC.TABLE_NAME = ?\n" +
            "  and ISC.TABLE_SCHEMA = ?\n";

    private static final String COLUMN_NAME_FILTER = "AND ISC.COLUMN_NAME = ?";

    private final JdbcTemplate jdbcTemplate;

    public HsqlDbGetTableInfoAction(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EzasseTableInfo> doAction(GetTableInfoActionParam actionParam) {
        List<Map<String, Object>> tableInfos;
        if (StringUtils.isNotBlank(actionParam.getColumnName())) {
            tableInfos = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL + COLUMN_NAME_FILTER, actionParam.getTableName(), getDataBaseNameFromDataSource(jdbcTemplate.getDataSource()), actionParam.getColumnName());
        } else {
            tableInfos = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL, actionParam.getTableName(), getDataBaseNameFromDataSource(jdbcTemplate.getDataSource()));
        }
        return toTableInfo(tableInfos);
    }
}
