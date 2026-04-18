package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 执行器
 *
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
@Data
public class EzasseExecutor {

    protected static final Log log = LogFactory.getLog(EzasseExecutor.class);

    protected ExecutorManager executorManager;

    private String dataSourceType;

    public EzasseExecutor(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

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
    protected EzasseExecutorAction<? extends ActionParam, ?> getAction(String actionId) {
        return executorManager.getExecutorAction(getDataSourceType(), actionId);
    }

}
