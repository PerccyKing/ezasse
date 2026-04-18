package cn.com.pism.ezasse.jdbc.register;

import cn.com.pism.ezasse.jdbc.action.mysql.MysqlGetTableInfoAction;
import cn.com.pism.ezasse.jdbc.action.mysql.MysqlTableExistsAction;
import cn.com.pism.ezasse.jdbc.executor.JdbcTemplateExecutor;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.MARIADB;
import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.MYSQL;

/**
 * @author PerccyKing
 * @since 25-02-14 22:40
 */
public class JdbcMysqlExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        executorManager.registerExecutorAction(MYSQL, MysqlGetTableInfoAction.build());
        executorManager.registerExecutorAction(MYSQL, new MysqlTableExistsAction());

        // 注册 mysql 的 executor
        executorManager.registerExecutor(new JdbcTemplateExecutor(MYSQL));

        // 兼容 MariaDB
        executorManager.registerExecutorAction(MARIADB, MysqlGetTableInfoAction.build());
        executorManager.registerExecutorAction(MARIADB, new MysqlTableExistsAction());
        executorManager.registerExecutor(new JdbcTemplateExecutor(MARIADB));
    }
}
