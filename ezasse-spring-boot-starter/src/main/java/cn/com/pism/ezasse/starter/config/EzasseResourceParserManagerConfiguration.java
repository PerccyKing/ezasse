package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.ResourceParserManager;
import cn.com.pism.ezasse.manager.impl.DefaultResourceParserManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author PerccyKing
 * @since 25-03-15 23:28
 */
public class EzasseResourceParserManagerConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResourceParserManager.class)
    public ResourceParserManager resourceParserManager() {
        return new DefaultResourceParserManager();
    }

}
