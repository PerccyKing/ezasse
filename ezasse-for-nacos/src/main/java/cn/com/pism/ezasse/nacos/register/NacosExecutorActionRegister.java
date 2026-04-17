package cn.com.pism.ezasse.nacos.register;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.ExecutorActionRegister;
import cn.com.pism.ezasse.nacos.action.NacosDoExecuteDefaultAction;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;

/**
 * @author PerccyKing
 * @since 25-06-19 22:29
 */
public class NacosExecutorActionRegister implements ExecutorActionRegister {

    public void registry(ExecutorManager executorManager) {
        // 注册公共动作
        executorManager.registerExecutorAction(EzasseForNacosConstant.NACOS, new NacosDoExecuteDefaultAction());
    }
}
