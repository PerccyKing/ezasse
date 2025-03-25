package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.JdbcTemplateCache;
import cn.com.pism.ezasse.jdbc.util.SqlTypeUtil;
import cn.com.pism.ezasse.model.ActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * @author PerccyKing
 * @since 25-03-23 14:20
 */
public abstract class MysqlDoChangeAction<P extends ActionParam> implements EzasseExecutorAction<P, Boolean> {

    private static final Log log = LogFactory.getLog(MysqlDoChangeAction.class);

    protected static final String DDL_QUERY_SQL = "SHOW CREATE TABLE ";

    @Override
    public Boolean doAction(P actionParam, EzasseDataSource dataSource) {
        SqlTypeUtil.init(dataSource.getType(), dataSource.getDataSource());
        JdbcTemplate jdbcTemplate = JdbcTemplateCache.get(dataSource.getId());
        String tableDdl = getTableDdl(jdbcTemplate, getTableName(actionParam));
        String changeSql = buildChangeSql(tableDdl, actionParam);
        if (StringUtils.isBlank(changeSql)) {
            return false;
        }
        EzasseLogUtil.info(log, String.format("do change sql [%s]", changeSql));
        jdbcTemplate.execute(changeSql);
        return true;
    }

    abstract String getTableName(P param);

    abstract String buildChangeSql(String tableDdl, P param);

    protected String getTableDdl(JdbcTemplate jdbcTemplate, String tableName) {
        Map<String, Object> ddlMap = jdbcTemplate.queryForMap(DDL_QUERY_SQL + tableName);
        return ddlMap.get("Create Table").toString();
    }
}
