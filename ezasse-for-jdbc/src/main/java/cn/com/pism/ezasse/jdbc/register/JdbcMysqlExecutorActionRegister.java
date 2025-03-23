package cn.com.pism.ezasse.jdbc.register;

import cn.com.pism.ezasse.jdbc.action.mysql.MysqlGetTableInfoAction;
import cn.com.pism.ezasse.jdbc.action.mysql.MysqlTableExistsAction;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.MYSQL;

/**
 * @author PerccyKing
 * @since 25-02-14 22:40
 */
public class JdbcMysqlExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        executorManager.registerExecutorAction(MYSQL, MysqlGetTableInfoAction.build());
        executorManager.registerExecutorAction(MYSQL, new MysqlTableExistsAction());
    }
}
