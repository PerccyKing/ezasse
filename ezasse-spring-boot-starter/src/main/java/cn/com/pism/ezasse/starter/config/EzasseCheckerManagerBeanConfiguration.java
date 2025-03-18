package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.CheckerManager;
import cn.com.pism.ezasse.manager.impl.DefaultCheckerManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author PerccyKing
 * @since 25-03-16 11:34
 */
public class EzasseCheckerManagerBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean(CheckerManager.class)
    public CheckerManager checkerManager() {
        return new DefaultCheckerManager();
    }

}
