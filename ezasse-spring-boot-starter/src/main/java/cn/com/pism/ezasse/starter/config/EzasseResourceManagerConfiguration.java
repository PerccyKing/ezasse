package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.ResourceManager;
import cn.com.pism.ezasse.manager.impl.DefaultResourceManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author PerccyKing
 * @since 25-03-16 14:44
 */
public class EzasseResourceManagerConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResourceManager.class)
    public ResourceManager resourceManager() {
        return new DefaultResourceManager();
    }
}
