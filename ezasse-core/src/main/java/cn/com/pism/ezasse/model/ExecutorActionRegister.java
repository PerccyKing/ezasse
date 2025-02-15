package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.manager.ExecutorManager;

/**
 * 执行器action注册器
 *
 * @author PerccyKing
 * @since 25-02-14 21:56
 */
public interface ExecutorActionRegister {

    void registry(ExecutorManager executorManager);
}
