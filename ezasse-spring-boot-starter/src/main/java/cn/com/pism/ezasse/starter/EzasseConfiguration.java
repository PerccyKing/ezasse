package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.Ezasse;
import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 11:07
 */
@Configuration
@ConditionalOnClass(Ezasse.class)
@EnableConfigurationProperties(EzasseProperties.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
public class EzasseConfiguration implements ApplicationContextAware {

    private static final Log log = LogFactory.getLog(EzasseConfiguration.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public Ezasse init() {
        log.info("Ezasse - Starting...");
        Ezasse ezasse = new Ezasse();
        EzasseProperties ezasseProperties = applicationContext.getBean(EzasseProperties.class);
        EzasseConfig ezasseConfig = new EzasseConfig();
        BeanUtils.copyProperties(ezasseProperties, ezasseConfig);
        ezasse.setConfig(ezasseConfig);
        ezasse.initChecker();
        //添加自定义校验器
        Map<String, EzasseChecker> ezasseCheckerMap = applicationContext.getBeansOfType(EzasseChecker.class);
        ezasseCheckerMap.forEach((s, ezasseChecker) -> {
            log.info("Ezasse - Add custom Checker :" + ezasseChecker.getId(ezasseConfig));
            ezasse.addChecker(ezasseChecker);
        });
        //添加自定义执行器
        Map<String, EzasseExecutor> ezasseExecutorMap = applicationContext.getBeansOfType(EzasseExecutor.class);
        ezasseExecutorMap.forEach((s, ezasseExecutor) -> {
            log.info("Ezasse - Add custom Executor :" + ezasseExecutor.getId());
            ezasse.addExecutor(ezasseExecutor);
        });
        String[] ezDatasource = applicationContext.getBeanNamesForType(EzasseDatasource.class);
        if (ArrayUtils.isNotEmpty(ezDatasource)) {
            EzasseDatasource ezasseDatasource = applicationContext.getBean(ezDatasource[0], EzasseDatasource.class);
            ezasseDatasource.getDataSource().forEach(ezasse::addDataSource);
            if (ezasseDatasource.getMaster() != null) {
                ezasse.setMaster(ezasseDatasource.getMaster());
            }
        } else {
            JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
            ezasse.setMaster(jdbcTemplate.getDataSource());
        }
        ezasse.executeScript();
        return ezasse;
    }
}
