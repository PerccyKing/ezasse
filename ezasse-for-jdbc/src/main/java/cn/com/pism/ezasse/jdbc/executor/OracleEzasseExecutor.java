package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.jdbc.action.oracle.OracleGetTableInfoAction;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author PerccyKing
 * @since 25-01-27 15:20
 */
public class OracleEzasseExecutor extends JdbcTemplateExecutor {

    public OracleEzasseExecutor(EzasseDataSource ezasseDataSource) {
        super(ezasseDataSource);
    }

    @Override
    protected void registerActions(EzasseDataSource ezasseDataSource, JdbcTemplate jdbcTemplate) {
        registerAction(new OracleGetTableInfoAction(jdbcTemplate));
    }
}
