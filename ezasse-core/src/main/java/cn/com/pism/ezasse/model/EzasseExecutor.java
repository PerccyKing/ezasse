package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 执行器
 *
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
@Setter
public abstract class EzasseExecutor {

    protected static final Log log = LogFactory.getLog(EzasseExecutor.class);

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
            EzasseLogUtil.trace(log, String.format("executor:%s, EzasseExecutorAction [%s] not found", getDataSourceType(), actionId));
            // 没有action处理
            return null;
        }
        R r = (R) executorAction.doAction(param, dataSource);
        EzasseLogUtil.trace(log, String.format("executor:%s, execute '%s', param:%s, result:%s", getDataSourceType(), actionId, param, r));
        return r;
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
