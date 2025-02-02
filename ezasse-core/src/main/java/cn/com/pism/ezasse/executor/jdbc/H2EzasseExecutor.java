package cn.com.pism.ezasse.executor.jdbc;

import cn.com.pism.ezasse.action.jdbc.h2.H2GetTableInfoAction;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author PerccyKing
 * @since 25-01-27 15:37
 */
public class H2EzasseExecutor extends JdbcTemplateExecutor {
    protected H2EzasseExecutor(EzasseDataSource ezasseDataSource) {
        super(ezasseDataSource);
    }

    @Override
    protected void registerActions(EzasseDataSource ezasseDataSource, JdbcTemplate jdbcTemplate) {
        registerAction(H2GetTableInfoAction.build(jdbcTemplate));
    }
}
