package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.executor.EzasseExecutor;

import java.util.Map;

/**
 * @author PerccyKing
 * @since 25-02-06 23:40
 */
public interface EzasseExecutorRegister {

    Map<String, Class<? extends EzasseExecutor>> getExecutorMap();
}
