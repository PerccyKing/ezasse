package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.manager.impl.DefaultExecutorManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author PerccyKing
 * @since 25-03-16 13:00
 */
public class EzasseExecutorManagerBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean(ExecutorManager.class)
    public ExecutorManager executorManager() {
        return new DefaultExecutorManager();
    }

}
