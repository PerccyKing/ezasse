package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.action.ActionParam;
import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.context.EzasseContextHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 执行器
 *
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
public abstract class EzasseExecutor {

    /**
     * 执行器依赖的数据源
     */
    protected EzasseDataSource ezasseDataSource;

    /**
     * 执行器动作库
     */
    protected Map<String, EzasseExecutorAction<? extends ActionParam, ?>> ezasseExecutorActionMap = new ConcurrentHashMap<>(16);

    protected EzasseExecutor(EzasseDataSource ezasseDataSource) {
        this.ezasseDataSource = ezasseDataSource;

        preProcess(ezasseDataSource);

        // 获取当前执行器的动作
        List<EzasseExecutorAction<? extends ActionParam, ?>> executorActions
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
    public <R, P extends ActionParam> R execute(String action, P param) {
        EzasseExecutorAction<P, ?> ezasseExecutorAction = (EzasseExecutorAction<P, ?>) getEzasseExecutorAction(action);
        return (R) ezasseExecutorAction.doAction(param);
    }

    @SuppressWarnings("all")
    public EzasseExecutorAction<? extends ActionParam, ?> getEzasseExecutorAction(String action) {
        return ezasseExecutorActionMap.get(action);
    }

    public void registerAction(EzasseExecutorAction<? extends ActionParam, ?> ezasseExecutorAction) {
        registerAction(ezasseExecutorAction.getId(), ezasseExecutorAction);
    }

    public void registerAction(String action, EzasseExecutorAction<? extends ActionParam, ?> ezasseExecutorAction) {
        ezasseExecutorActionMap.put(action, ezasseExecutorAction);
    }

}
