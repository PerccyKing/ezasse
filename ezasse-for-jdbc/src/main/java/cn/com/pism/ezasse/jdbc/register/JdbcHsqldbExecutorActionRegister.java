package cn.com.pism.ezasse.jdbc.register;

import cn.com.pism.ezasse.jdbc.action.hsqldb.HsqlDbGetTableInfoAction;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.HSQLDB;

/**
 * @author PerccyKing
 * @since 25-02-14 21:10
 */
public class JdbcHsqldbExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        executorManager.registerExecutorAction(HSQLDB, new HsqlDbGetTableInfoAction());
    }

}
