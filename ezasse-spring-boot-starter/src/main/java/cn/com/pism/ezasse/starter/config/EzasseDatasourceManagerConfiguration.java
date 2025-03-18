package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.DatasourceManager;
import cn.com.pism.ezasse.manager.impl.DefaultDatasourceManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实例化ezasse 的数据源管理器
 *
 * @author PerccyKing
 * @since 25-03-13 22:36
 */
@Configuration
public class EzasseDatasourceManagerConfiguration {

    @Bean
    @ConditionalOnMissingBean(DatasourceManager.class)
    public DatasourceManager datasourceManager() {
        return new DefaultDatasourceManager();
    }
}
