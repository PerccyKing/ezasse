package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.action.param.ActionParam;
import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 执行器管理器默认实现
 *
 * @author PerccyKing
 * @since 25-01-01 12:53
 */
public class DefaultExecutorManager implements ExecutorManager {

    /**
     * 数据源类型，对应的执行器类型
     */
    private final Map<String, Class<? extends EzasseExecutor>> executorClassMap = new ConcurrentHashMap<>(16);

    /**
     * 执行器map [id,执行器]
     */
    private final Map<String, EzasseExecutor> executorMap = new ConcurrentHashMap<>(16);

    /**
     * 执行器对应的执行动作map
     */
    private final Map<Class<? extends EzasseExecutor>, List<EzasseExecutorAction<? extends ActionParam, ?>>> executorActionMap = new ConcurrentHashMap<>(16);

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
    @Override
    public EzasseExecutor getExecutor(String dataSourceId) {
        return executorMap.computeIfAbsent(dataSourceId, id -> {
            // 获取数据源
            EzasseDataSource dataSource = EzasseContextHolder.getContext().datasourceManager().getDataSource(id);

            // 通过数据源类型获取执行器的类型
            Class<? extends EzasseExecutor> ezasseExecutorClass = executorClassMap.get(dataSource.getType());
            try {
                // 实例化执行器
                return ezasseExecutorClass.getConstructor(EzasseDataSource.class).newInstance(dataSource);
            } catch (Exception e) {
                throw new EzasseException("really?!!");
            }
        });
    }

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
    @Override
    public void registerExecutor(String dataSourceType, Class<? extends EzasseExecutor> executorClass) {
        executorClassMap.put(dataSourceType, executorClass);
    }


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
    @Override
    public void registerExecutorAction(Class<? extends EzasseExecutor> executorType,
                                       EzasseExecutorAction<? extends ActionParam, ?> executorAction) {
        List<EzasseExecutorAction<? extends ActionParam, ?>> ezasseExecutorActions =
                executorActionMap.computeIfAbsent(executorType, k -> new ArrayList<>());
        ezasseExecutorActions.add(executorAction);
    }

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
    @Override
    public List<EzasseExecutorAction<? extends ActionParam, ?>> getExecutorAction(Class<? extends EzasseExecutor> executorType) {
        List<EzasseExecutorAction<? extends ActionParam, ?>> ezasseExecutorActions = executorActionMap.get(executorType);
        if (CollectionUtils.isEmpty(ezasseExecutorActions)) {
            return Collections.emptyList();
        }
        return ezasseExecutorActions;
    }
}
