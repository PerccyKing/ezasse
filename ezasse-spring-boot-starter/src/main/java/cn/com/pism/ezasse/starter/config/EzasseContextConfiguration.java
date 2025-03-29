package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.context.DefaultEzasseContext;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.manager.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author PerccyKing
 * @since 25-02-19 21:36
 */
@Import({
        EzasseDatasourceManagerConfiguration.class,
        EzasseCheckerManagerConfiguration.class,
        EzasseExecutorManagerConfiguration.class,
        EzasseResourceLoaderManagerConfiguration.class,
        EzasseResourceParserManagerConfiguration.class,
        EzasseConfigManagerConfiguration.class,
        EzasseResourceManagerConfiguration.class
})
@Configuration
public class EzasseContextConfiguration {

    @Bean
    @ConditionalOnMissingBean(EzasseContext.class)
    public EzasseContext ezasseContext(DatasourceManager datasourceManager,
                                       CheckerManager checkerManager,
                                       ExecutorManager executorManager,
                                       ResourceLoaderManager resourceLoaderManager,
                                       ResourceParserManager resourceParserManager,
                                       ConfigManager configManager,
                                       ResourceManager resourceManager) {
        return new DefaultEzasseContext(datasourceManager,
                checkerManager,
                executorManager,
                resourceLoaderManager,
                resourceParserManager,
                configManager,
                resourceManager);
    }


}
