package cn.com.pism.ezasse.starter.config.jdbc;

import cn.com.pism.ezasse.jdbc.register.JdbcEzasseDataSource;
import cn.com.pism.ezasse.manager.DatasourceManager;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.com.pism.ezasse.constants.EzasseConstants.MASTER;

/**
 * @author PerccyKing
 * @since 25-02-19 22:25
 */
@Import({EzasseV0DatasourceRegister.class})
public class EzasseForJdbcConfiguration {

    private static final Log log = LogFactory.getLog(EzasseForJdbcConfiguration.class);

    @Resource
    private EzasseV0DatasourceRegister ezasseV0DatasourceRegister;

    private ApplicationContext applicationContext;

    @Autowired
    public void ezasseForJdbc(ApplicationContext applicationContext, DatasourceManager datasourceManager) {
        this.applicationContext = applicationContext;

        // 兼容0.x版本的数据源
        ezasseV0DatasourceRegister.register(datasourceManager);

        // 获取所有的数据源
        Map<String, EzasseDataSource> ezasseDataSourceMap = getAllEzasseDataSource();


        if (!CollectionUtils.isEmpty(ezasseDataSourceMap)) {
            ezasseDataSourceMap.values().forEach(datasourceManager::registerDataSource);
        }

        // 如果没有数据源，尝试使用jdbcTemplate 的数据源
        if (datasourceManager.isEmpty()) {
            JdbcTemplate jdbcTemplate = null;
            try {
                jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
            } catch (BeansException e) {
                EzasseLogUtil.warn(log, "no jdbcTemplate instance found");
            }
            if (jdbcTemplate == null) {
                return;
            }
            datasourceManager.registerDataSource(new JdbcEzasseDataSource(jdbcTemplate.getDataSource(), MASTER));
        }
    }

    private Map<String, EzasseDataSource> getAllEzasseDataSource() {
        return filterJdbcDataSource(applicationContext.getBeansOfType(EzasseDataSource.class));
    }

    private Map<String, EzasseDataSource> filterJdbcDataSource(Map<String, EzasseDataSource> ezasseDataSourceMap) {
        return ezasseDataSourceMap.entrySet().stream()
                .filter(entry -> {
                    Object dataSource = entry.getValue().getDataSource();
                    return dataSource instanceof DataSource;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
