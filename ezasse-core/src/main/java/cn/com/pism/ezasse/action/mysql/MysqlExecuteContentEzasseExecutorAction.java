package cn.com.pism.ezasse.action.mysql;

import cn.com.pism.ezasse.action.EzasseExecuteContentActionParam;
import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;

/**
 * @author PerccyKing
 * @since 24-12-29 16:46
 */
public class MysqlExecuteContentEzasseExecutorAction implements EzasseExecutorAction<EzasseExecuteContentActionParam, Object> {

    protected static final Log log = LogFactory.getLog(MysqlExecuteContentEzasseExecutorAction.class);


    private final JdbcTemplate jdbcTemplate;

    public MysqlExecuteContentEzasseExecutorAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object doAction(EzasseExecuteContentActionParam actionParam) {
        try {
            ByteArrayResource resource = new ByteArrayResource(actionParam.getContent().getBytes());
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
        } catch (SQLException e) {
            EzasseLogUtil.error(log, e.getMessage());
        }
        return null;
    }

    @Override
    public String getId() {
        return "";
    }
}
