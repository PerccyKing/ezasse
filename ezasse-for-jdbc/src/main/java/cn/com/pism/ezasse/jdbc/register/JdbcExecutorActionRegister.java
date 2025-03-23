package cn.com.pism.ezasse.jdbc.register;

import cn.com.pism.ezasse.jdbc.action.DefaultCheckAction;
import cn.com.pism.ezasse.jdbc.action.DefaultDoExecuteAction;
import cn.com.pism.ezasse.jdbc.executor.JdbcTemplateExecutor;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;

/**
 * @author PerccyKing
 * @since 25-02-14 22:09
 */
public class JdbcExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        // 注册公共动作
        executorManager.registerExecutorAction(JdbcTemplateExecutor.class.getName(), new DefaultCheckAction());
        executorManager.registerExecutorAction(JdbcTemplateExecutor.class.getName(), new DefaultDoExecuteAction());
    }
}
