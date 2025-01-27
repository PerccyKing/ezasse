package cn.com.pism.ezasse.action.jdbc;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
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
 * @since 25-01-27 15:30
 */
public abstract class GetTableInfoAction implements EzasseExecutorAction<GetTableInfoActionParam, List<EzasseTableInfo>> {

    private final JdbcTemplate jdbcTemplate;

    protected GetTableInfoAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EzasseTableInfo> doAction(GetTableInfoActionParam actionParam) {
        List<Map<String, Object>> queryForList;
        if (StringUtils.isNotBlank(actionParam.getColumnName())) {
            queryForList = jdbcTemplate.queryForList(getTableInfoSql() + getFieldFilterSql(), actionParam.getTableName(), getDataBaseNameFromDataSource(this.jdbcTemplate.getDataSource()), actionParam.getColumnName());
        } else {
            queryForList = jdbcTemplate.queryForList(getTableInfoSql(), actionParam.getTableName(), getDataBaseNameFromDataSource(this.jdbcTemplate.getDataSource()));
        }
        return toTableInfo(queryForList);
    }


    protected abstract String getTableInfoSql();

    protected abstract String getFieldFilterSql();
}
