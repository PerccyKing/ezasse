package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ActionParam;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.util.CollUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 执行器管理器默认实现
 *
 * @author PerccyKing
 * @since 25-01-01 12:53
 */
public class DefaultExecutorManager implements ExecutorManager {

    /**
     * 执行器map [datasourceType,执行器]
     */
    private final Map<String, EzasseExecutor> executorMap = new ConcurrentHashMap<>(16);

    /**
     * 执行动作map [datasourceType,执行器动作]
     */
    private final Map<String, List<EzasseExecutorAction<? extends ActionParam, ?>>> executorActionMap = new ConcurrentHashMap<>(16);

    /**
     * 执行器动作id map [执行器类型, [动作id,动作]]]
     */
    private final Map<String, Map<String, EzasseExecutorAction<? extends ActionParam, ?>>> executorActionIdMap = new HashMap<>(16);

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
    @Override
    public EzasseExecutor getExecutor(String dataSourceType) {
        return executorMap.get(dataSourceType);
    }

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
    @Override
    public void registerExecutor(String dataSourceType, EzasseExecutor executor) {
        executorMap.put(dataSourceType, executor);
    }


    /**
     * <p>
     * 向执行器注册动作
     * </p>
     * by perccyking
     *
     * @param dataSourceType : 执行器类型
     * @param executorAction : 动作
     * @since 25-01-01 01:08
     */
    @Override
    public void registerExecutorAction(String dataSourceType,
                                       EzasseExecutorAction<? extends ActionParam, ?> executorAction) {

        List<EzasseExecutorAction<? extends ActionParam, ?>> ezasseExecutorActions =
                executorActionMap.computeIfAbsent(dataSourceType, k -> new ArrayList<>());
        ezasseExecutorActions.add(executorAction);

        Map<String, EzasseExecutorAction<? extends ActionParam, ?>> idExecutorActionMap =
                executorActionIdMap.computeIfAbsent(dataSourceType, k -> new HashMap<>(16));
        idExecutorActionMap.put(executorAction.getId(), executorAction);
    }

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
    @Override
    public List<EzasseExecutorAction<? extends ActionParam, ?>> getExecutorAction(String dataSourceType) {
        List<EzasseExecutorAction<? extends ActionParam, ?>> executorActions = executorActionMap.get(dataSourceType);
        if (CollUtils.isEmpty(executorActions)) {
            return Collections.emptyList();
        }
        return executorActions;
    }

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
    @Override
    public EzasseExecutorAction<? extends ActionParam, ?> getExecutorAction(String dataSourceType, String actionId) {
        Map<String, EzasseExecutorAction<? extends ActionParam, ?>> actionMap = executorActionIdMap.getOrDefault(dataSourceType, new HashMap<>());
        if (actionMap.get(actionId) == null) {
            return actionMap.get(actionId);
        }
        return executorActionIdMap.getOrDefault(dataSourceType, new HashMap<>()).get(actionId);
    }
}
