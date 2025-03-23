package cn.com.pism.ezasse.jdbc.register;

import cn.com.pism.ezasse.jdbc.action.h2.H2GetTableInfoAction;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.H2;

/**
 * @author PerccyKing
 * @since 25-02-14 13:12
 */
public class JdbcH2ExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        executorManager.registerExecutorAction(H2, H2GetTableInfoAction.build());
    }
}