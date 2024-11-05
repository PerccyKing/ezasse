package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.model.EzasseConfig;

/**
 * @author PerccyKing
 * @since 24-06-29 18:15
 */
public interface EzasseConfigPostProcessor {
    default void postProcessAfterInitialization(EzasseConfig ezasseConfig) {
        // do nothing
    }
}
