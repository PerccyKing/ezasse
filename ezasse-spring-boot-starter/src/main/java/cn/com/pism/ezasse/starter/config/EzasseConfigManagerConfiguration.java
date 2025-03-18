package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.ConfigManager;
import cn.com.pism.ezasse.manager.impl.DefaultConfigManager;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.starter.EzasseProperties;
import cn.com.pism.ezasse.starter.EzassePropertitesToConfigFunction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author PerccyKing
 * @since 25-03-16 14:40
 */
public class EzasseConfigManagerConfiguration {

    private final EzassePropertitesToConfigFunction function = new EzassePropertitesToConfigFunction();

    @Bean
    @ConditionalOnMissingBean(ConfigManager.class)
    public ConfigManager configManager(EzasseProperties ezasseProperties) {
        DefaultConfigManager configManager = new DefaultConfigManager();
        EzasseConfig ezasseConfig = function.apply(ezasseProperties);
        configManager.setConfig(ezasseConfig);
        return configManager;
    }
}
