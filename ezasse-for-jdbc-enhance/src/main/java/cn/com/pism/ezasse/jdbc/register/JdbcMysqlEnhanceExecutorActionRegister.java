package cn.com.pism.ezasse.jdbc.register;

import cn.com.pism.ezasse.jdbc.action.mysql.MysqlChangeFieldCommentAction;
import cn.com.pism.ezasse.jdbc.action.mysql.MysqlChangeFieldNameAction;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.MYSQL;

/**
 * @author PerccyKing
 * @since 25-03-23 09:49
 */
public class JdbcMysqlEnhanceExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        executorManager.registerExecutorAction(MYSQL, new MysqlChangeFieldCommentAction());
        executorManager.registerExecutorAction(MYSQL, new MysqlChangeFieldNameAction());
    }
}