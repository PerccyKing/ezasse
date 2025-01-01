package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.action.param.ActionParam;
import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.model.EzasseExecutor;

import java.util.List;

/**
 * 执行器管理器
 *
 * @author PerccyKing
 * @since 25-01-01 12:53
 */
public interface ExecutorManager {

    /**
     * <p>
     * 根据数据源id获取执行器
     * </p>
     * by perccyking
     *
     * @param dataSourceId : 数据源id
     * @return {@link EzasseExecutor} 执行器
     * @since 24-12-29 17:52
     */
    EzasseExecutor getExecutor(String dataSourceId);

    /**
     * <p>
     * 注册执行器
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 数据源类型
     * @param executorClass  : 执行器类型
     * @since 24-12-29 17:52
     */
    void registerExecutor(String dataSourceType, Class<? extends EzasseExecutor> executorClass);


    /**
     * <p>
     * 向执行器注册动作
     * </p>
     * by perccyking
     *
     * @param executorType   : 执行器类型
     * @param executorAction : 动作
     * @since 25-01-01 01:08
     */
    void registerExecutorAction(Class<? extends EzasseExecutor> executorType, EzasseExecutorAction<? extends ActionParam, ?> executorAction);

    /**
     * <p>
     * 获取已注册的执行器动作
     * </p>
     * by perccyking
     *
     * @param executorType : 执行器类型
     * @return 动作列表
     * @since 25-01-01 01:09
     */
    @SuppressWarnings("all")
    List<EzasseExecutorAction<? extends ActionParam, ?>> getExecutorAction(Class<? extends EzasseExecutor> executorType);

}
