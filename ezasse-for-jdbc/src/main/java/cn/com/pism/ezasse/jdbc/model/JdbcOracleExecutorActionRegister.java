package cn.com.pism.ezasse.jdbc.model;

import cn.com.pism.ezasse.jdbc.action.oracle.OracleGetTableInfoAction;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.ORACLE;

/**
 * @author PerccyKing
 * @since 25-02-14 23:06
 */
public class JdbcOracleExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        executorManager.registerExecutorAction(ORACLE, new OracleGetTableInfoAction());
    }
}
