package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.manager.ExecutorManager;
import lombok.Setter;

/**
 * 执行器
 *
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
@Setter
public abstract class EzasseExecutor {

    protected ExecutorManager executorManager;

    /**
     * 执行action
     *
     * @param actionId   actionId
     * @param param      action参数
     * @param dataSource 数据源对象
     * @param <R>        响应
     * @param <P>        参数
     * @return action执行结果
     */
    @SuppressWarnings("unchecked")
    public <R, P extends ActionParam> R execute(String actionId, P param, EzasseDataSource dataSource) {
        EzasseExecutorAction<P, ?> executorAction = (EzasseExecutorAction<P, ?>) getAction(actionId);
        if (executorAction == null) {
            // 没有action处理
            return null;
        }
        return (R) executorAction.doAction(param, dataSource);
    }

    /**
     * 获取action
     *
     * @param actionId actionId
     * @return 执行器action实例
     */
    @SuppressWarnings("all")
    protected EzasseExecutorAction<? extends ActionParam, ?> getAction(String actionId) {
        EzasseExecutorAction<? extends ActionParam, ?> executorAction = executorManager.getExecutorAction(getDataSourceType(), actionId);
        return executorAction;
    }

    /**
     * <p>
     * 获取执行器数据源类型
     * </p>
     * by perccyking
     *
     * @return 执行器数据源类型
     * @since 25-02-08 12:18
     */
    public abstract String getDataSourceType();

}
