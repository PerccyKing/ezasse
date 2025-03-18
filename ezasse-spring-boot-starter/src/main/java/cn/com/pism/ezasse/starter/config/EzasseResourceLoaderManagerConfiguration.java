package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.ResourceLoaderManager;
import cn.com.pism.ezasse.manager.impl.DefaultResourceLoaderManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author PerccyKing
 * @since 25-03-15 23:21
 */
public class EzasseResourceLoaderManagerConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResourceLoaderManager.class)
    public ResourceLoaderManager resourceLoaderManager() {
        return new DefaultResourceLoaderManager();
    }

}
