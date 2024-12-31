package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.EzasseExecutorActionParam;
import cn.com.pism.ezasse.context.EzasseContextHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
public abstract class EzasseExecutor {

    protected EzasseDataSource ezasseDataSource;

    protected Map<String, EzasseExecutorAction<? extends EzasseExecutorActionParam, ?>> ezasseExecutorActionMap = new ConcurrentHashMap<>(16);

    protected EzasseExecutor(EzasseDataSource ezasseDataSource) {
        this.ezasseDataSource = ezasseDataSource;

        preProcess(ezasseDataSource);

        // 获取注册的执行器
        List<EzasseExecutorAction<? extends EzasseExecutorActionParam, ?>> executorActions
                = EzasseContextHolder.getContext().getExecutorAction(this.getClass());
        executorActions.forEach(this::registerAction);
    }

    /**
     * <p>
     * 执行器实例化的前置处理
     * </p>
     * <p>
     * 执行器可以在这里预先注册默认的执行动作
     * </p>
     * by perccyking
     *
     * @param dataSource : 数据源
     * @since 25-01-01 01:24
     */
    protected void preProcess(EzasseDataSource dataSource) {
        // default do nothing
    }

    @SuppressWarnings("unchecked")
    public <R, P extends EzasseExecutorActionParam> R execute(String action, P param) {
        EzasseExecutorAction<P, ?> ezasseExecutorAction = (EzasseExecutorAction<P, ?>) getEzasseExecutorAction(action);
        return (R) ezasseExecutorAction.doAction(param);
    }

    @SuppressWarnings("all")
    public EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> getEzasseExecutorAction(String action) {
        return ezasseExecutorActionMap.get(action);
    }

    public void registerAction(EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> ezasseExecutorAction) {
        registerAction(ezasseExecutorAction.getId(), ezasseExecutorAction);
    }

    public void registerAction(String action, EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> ezasseExecutorAction) {
        ezasseExecutorActionMap.put(action, ezasseExecutorAction);
    }

}
