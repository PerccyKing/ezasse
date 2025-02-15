package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DO_EXECUTE;

/**
 * @author PerccyKing
 * @since 25-01-01 22:54
 */
public class DefaultDoExecuteAction implements EzasseExecutorAction<DoExecuteActionParam, Boolean> {

    protected static final Log log = LogFactory.getLog(DefaultDoExecuteAction.class);

    @Override
    @SuppressWarnings("all")
    public Boolean doAction(DoExecuteActionParam actionParam, EzasseDataSource dataSource) {
        try {
            ByteArrayResource resource = new ByteArrayResource(actionParam.getExecuteContent().getBytes());
            ScriptUtils.executeSqlScript(JdbcTemplateCache.get(dataSource.getId()).getDataSource().getConnection(), resource);
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
