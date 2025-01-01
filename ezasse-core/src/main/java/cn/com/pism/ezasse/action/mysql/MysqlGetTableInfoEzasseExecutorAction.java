package cn.com.pism.ezasse.action.mysql;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseTableInfo;
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
public class MysqlGetTableInfoEzasseExecutorAction implements EzasseExecutorAction<GetTableInfoActionParam, EzasseTableInfo> {

    private static final String GET_TABLE_INFO_SQL = "SELECT COLUMN_NAME columnName,DATA_TYPE dataType,CHARACTER_MAXIMUM_LENGTH characterMaximumLength,COLUMN_COMMENT columnComment FROM Information_schema.columns WHERE table_Name = ? AND TABLE_SCHEMA=? ";

    private final JdbcTemplate jdbcTemplate;

    public MysqlGetTableInfoEzasseExecutorAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public EzasseTableInfo doAction(GetTableInfoActionParam actionParam) {
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(GET_TABLE_INFO_SQL, actionParam.getTableName(), getDataBaseNameFromDataSource(this.jdbcTemplate.getDataSource()));
        return toTableInfo(queryForList).get(0);
    }

    @Override
    public String getId() {
        return GET_TABLE_INFO;
    }
}
