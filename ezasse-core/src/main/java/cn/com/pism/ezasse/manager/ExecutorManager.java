package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.model.ActionParam;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseExecutorAction;

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
     * @param dataSourceType : 数据源类型
     * @return {@link EzasseExecutor} 执行器
     * @since 24-12-29 17:52
     */
    EzasseExecutor getExecutor(String dataSourceType);

    /**
     * <p>
     * 注册执行器
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 数据源类型
     * @param executor       : 执行器实例
     * @since 24-12-29 17:52
     */
    void registerExecutor(String dataSourceType, EzasseExecutor executor);

    /**
     * <p>
     * 向执行器注册动作
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 数据源类型
     * @param executorAction : 动作
     * @since 25-01-01 01:08
     */
    void registerExecutorAction(String dataSourceType, EzasseExecutorAction<? extends ActionParam, ?> executorAction);

    /**
     * <p>
     * 获取已注册的执行器动作
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 数据源类型
     * @return 动作列表
     * @since 25-01-01 01:09
     */
    @SuppressWarnings("all")
    List<EzasseExecutorAction<? extends ActionParam, ?>> getExecutorAction(String dataSourceType);

    /**
     * <p>
     * 获取执行器的指定动作
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 数据源类型
     * @param actionId       : 动作id
     * @return 执行器
     * @since 25-02-08 12:15
     */
    @SuppressWarnings("all")
    EzasseExecutorAction<? extends ActionParam, ?> getExecutorAction(String dataSourceType, String actionId);

}
