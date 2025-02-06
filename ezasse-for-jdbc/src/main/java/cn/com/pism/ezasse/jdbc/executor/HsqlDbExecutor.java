package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.jdbc.action.hsqldb.HsqlDbGetTableInfoAction;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author PerccyKing
 * @since 25-02-02 22:43
 */
public class HsqlDbExecutor extends JdbcTemplateExecutor {

    public HsqlDbExecutor(EzasseDataSource ezasseDataSource) {
        super(ezasseDataSource);
    }

    @Override
    protected void registerActions(EzasseDataSource ezasseDataSource, JdbcTemplate jdbcTemplate) {
        registerAction(new HsqlDbGetTableInfoAction(jdbcTemplate));
    }
}
