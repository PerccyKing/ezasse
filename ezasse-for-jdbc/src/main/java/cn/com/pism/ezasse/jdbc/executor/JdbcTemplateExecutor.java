package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.model.ActionParam;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseExecutorAction;

/**
 * jdbc 执行器
 *
 * @author PerccyKing
 * @since 25-01-13 22:37
 */
public class JdbcTemplateExecutor extends EzasseExecutor {

    public JdbcTemplateExecutor(String dataSourceType) {
        super(dataSourceType);
    }

    @Override
    public EzasseExecutorAction<? extends ActionParam, ?> getAction(String actionId) {
        EzasseExecutorAction<? extends ActionParam, ?> action = super.getAction(actionId);
        if (action != null) {
            return action;
        }
        // 获取公共的action
        return executorManager.getExecutorAction(JdbcTemplateExecutor.class.getName(), actionId);
    }

}
