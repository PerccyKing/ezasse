package cn.com.pism.ezasse.starter.config.nacos;

import cn.com.pism.ezasse.manager.DatasourceManager;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.nacos.register.NacosEzasseDataSource;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Nacos 数据源自动配置
 * <p>
 * 当 classpath 中存在 {@link ConfigService} 和 {@link NacosEzasseDataSource} 时自动生效。
 * 负责将 Spring 容器中所有的 Nacos 类型 {@link EzasseDataSource} 注册到
 * {@link DatasourceManager} 中，使 ezasse 能够识别并使用 Nacos 数据源。
 * </p>
 *
 * @author PerccyKing
 * @since 26-04-18 00:20
 */
@Configuration
@ConditionalOnClass({ConfigService.class, NacosEzasseDataSource.class})
public class EzasseForNacosConfiguration {

    private static final Log log = LogFactory.getLog(EzasseForNacosConfiguration.class);

    /**
     * 自动发现并注册 Nacos 数据源
     * <p>
     * 从 Spring 容器中获取所有 {@link EzasseDataSource} 类型的 Bean，
     * 筛选出底层数据源为 {@link ConfigService} 的实例，注册到 {@link DatasourceManager}。
     * </p>
     *
     * @param applicationContext Spring 应用上下文
     * @param datasourceManager  ezasse 数据源管理器
     */
    @Autowired
    public void ezasseForNacos(ApplicationContext applicationContext,
                               DatasourceManager datasourceManager) {
        // 获取所有 EzasseDataSource 类型的 Bean，并筛选出 Nacos 类型
        Map<String, EzasseDataSource> nacosDataSourceMap = filterNacosDataSource(applicationContext.getBeansOfType(EzasseDataSource.class));

        if (!CollectionUtils.isEmpty(nacosDataSourceMap)) {
            nacosDataSourceMap.values().forEach(datasourceManager::registerDataSource);
            log.info("Registered " + nacosDataSourceMap.size() + " Nacos datasource(s) for ezasse");
        }
    }

    /**
     * 过滤出底层数据源为 {@link ConfigService} 的 EzasseDataSource
     *
     * @param ezasseDataSourceMap 所有 EzasseDataSource Bean
     * @return 仅包含 Nacos 类型数据源的 Map
     */
    private Map<String, EzasseDataSource> filterNacosDataSource(Map<String, EzasseDataSource> ezasseDataSourceMap) {
        return ezasseDataSourceMap.entrySet().stream()
                .filter(entry -> {
                    Object dataSource = entry.getValue().getDataSource();
                    return dataSource instanceof ConfigService;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
