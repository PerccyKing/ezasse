package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.action.DefaultCheckExecutorAction;
import cn.com.pism.ezasse.action.mysql.MysqlExecuteContentEzasseExecutorAction;
import cn.com.pism.ezasse.action.mysql.MysqlGetTableInfoEzasseExecutorAction;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author PerccyKing
 * @since 24-12-14 14:28
 */
public class MysqlEzasseExecutor extends EzasseExecutor {

    public MysqlEzasseExecutor(EzasseDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void preProcess(EzasseDataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.getDataSource());
        super.registerAction(new MysqlGetTableInfoEzasseExecutorAction(jdbcTemplate));
        super.registerAction(new MysqlExecuteContentEzasseExecutorAction(jdbcTemplate));
        super.registerAction(new DefaultCheckExecutorAction(jdbcTemplate));
    }
}
