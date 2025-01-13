package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.action.mysql.MysqlDoExecuteAction;
import cn.com.pism.ezasse.action.mysql.MysqlGetTableInfoEzasseExecutorAction;

/**
 * 执行器的mysql实现
 *
 * @author PerccyKing
 * @since 24-12-14 14:28
 */
public class MysqlEzasseExecutor extends JdbcTemplateExecutor {

    public MysqlEzasseExecutor(EzasseDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void preProcess(EzasseDataSource dataSource) {
        super.registerAction(new MysqlGetTableInfoEzasseExecutorAction(jdbcTemplate));
        super.registerAction(new MysqlDoExecuteAction(jdbcTemplate));
    }
}
