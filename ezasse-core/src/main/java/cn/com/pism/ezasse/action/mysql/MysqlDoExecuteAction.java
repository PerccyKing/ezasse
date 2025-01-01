package cn.com.pism.ezasse.action.mysql;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.param.DoExecuteParam;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DO_EXECUTE;

/**
 * @author PerccyKing
 * @since 25-01-01 22:54
 */
public class MysqlDoExecuteAction implements EzasseExecutorAction<DoExecuteParam, Boolean> {

    protected static final Log log = LogFactory.getLog(MysqlDoExecuteAction.class);


    private final JdbcTemplate jdbcTemplate;

    public MysqlDoExecuteAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @SuppressWarnings("all")
    public Boolean doAction(DoExecuteParam actionParam) {
        try {
            ByteArrayResource resource = new ByteArrayResource(actionParam.getExecuteContent().getBytes());
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
        } catch (SQLException e) {
            EzasseLogUtil.error(log, e.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * <p>
     * 获取动作id
     * </p>
     * by perccyking
     *
     * @return {@link String} 动作id
     * @since 25-01-01 11:19
     */
    @Override
    public String getId() {
        return DO_EXECUTE;
    }
}
