package cn.com.pism.ezasse.action.jdbc.mysql;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;
import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;
import static cn.com.pism.ezasse.util.EzasseUtil.toTableInfo;

/**
 * @author PerccyKing
 * @since 24-12-28 11:17
 */
public class MysqlGetTableInfoAction implements EzasseExecutorAction<GetTableInfoActionParam, List<EzasseTableInfo>> {

    private static final String GET_TABLE_INFO_SQL =
            "SELECT COLUMN_NAME columnName," +
                    "DATA_TYPE dataType," +
                    "CHARACTER_MAXIMUM_LENGTH characterMaximumLength," +
                    "COLUMN_COMMENT columnComment " +
                    "FROM Information_schema.columns WHERE TABLE_NAME = ? AND TABLE_SCHEMA=? ";

    private static final String COLUMN_FILTER = "AND COLUMN_NAME = ?";

    private final JdbcTemplate jdbcTemplate;

    public MysqlGetTableInfoAction(JdbcTemplate jdbcTemplate) {
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
