package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseExecutorAction;

/**
 * jdbc 执行器
 *
 * @author PerccyKing
 * @since 25-01-13 22:37
 */
public abstract class JdbcTemplateExecutor implements EzasseExecutor {

    @Override
    @SuppressWarnings("unchecked")
    public <R, P extends ActionParam> R execute(String actionId, P param, EzasseDataSource dataSource) {
        EzasseExecutorAction<P, ?> executorAction = (EzasseExecutorAction<P, ?>) getAction(actionId);
        if (executorAction == null) {
            // 没有action处理
            return null;
        }
        return (R) executorAction.doAction(param, dataSource);
    }

    @Override
    public EzasseExecutorAction<? extends ActionParam, ?> getAction(String actionId) {
        ExecutorManager executorManager = EzasseContextHolder.getContext().executorManager();
        EzasseExecutorAction<? extends ActionParam, ?> executorAction = executorManager.getExecutorAction(getDataSourceType(), actionId);
        if (executorAction != null) {
            return executorAction;
        }

        return executorManager.getExecutorAction(JdbcTemplateExecutor.class.getName(), actionId);
    }

}
