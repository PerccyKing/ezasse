package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.model.AbstractDoExecuteAction;
import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;

/**
 * @author PerccyKing
 * @since 25-01-01 22:54
 */
public class DefaultDoExecuteAction extends AbstractDoExecuteAction {

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

}
