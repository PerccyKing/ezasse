package cn.com.pism.ezasse.nacos.register;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.nacos.action.NacosDoExecuteDefaultAction;
import cn.com.pism.ezasse.nacos.action.NacosMergeToAction;
import cn.com.pism.ezasse.nacos.action.NacosPublishAction;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;

/**
 * Nacos 执行器动作注册器
 * <p>
 * 负责将 Nacos 相关的执行动作（Action）注册到核心执行管理器中。
 * </p>
 *
 * @author PerccyKing
 * @since 25-06-19 22:29
 */
public class NacosExecutorActionRegister implements ExecutorActionRegister {

    /**
     * 注册执行动作
     *
     * @param executorManager 执行器管理器
     */
    @Override
    public void registry(ExecutorManager executorManager) {
        // 注册公共动作，将 Nacos 数据源类型的默认动作映射到 NacosDoExecuteDefaultAction (Orchestrator)
        executorManager.registerExecutorAction(EzasseForNacosConstant.NACOS, new NacosDoExecuteDefaultAction());
        // 注册具体的 Nacos 执行动作
        executorManager.registerExecutorAction(EzasseForNacosConstant.NACOS, new NacosPublishAction());
        executorManager.registerExecutorAction(EzasseForNacosConstant.NACOS, new NacosMergeToAction());

        // 动态注册对应的 Executor
        executorManager.registerExecutor(new EzasseExecutor(EzasseForNacosConstant.NACOS));
    }
}
