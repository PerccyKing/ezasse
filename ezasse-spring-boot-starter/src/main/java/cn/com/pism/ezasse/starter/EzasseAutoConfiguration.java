package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.AbstractEzasse;
import cn.com.pism.ezasse.starter.config.EzasseContextConfiguration;
import cn.com.pism.ezasse.starter.config.jdbc.EzasseForJdbcConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * v1.x 自动配置类
 * </p>
 * <p>初始化ezasse需要的管理器</p>
 * <p></p>
 * <p>创建{@code ezasse} 实例</p>
 *
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 11:07
 */
@ConditionalOnClass(AbstractEzasse.class)
@ConditionalOnProperty(value = "spring.ezasse.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(EzasseProperties.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
@Import({
        EzasseContextConfiguration.class,
        EzasseForJdbcConfiguration.class,
        FileEzasseConfiguration.class
})
public class EzasseAutoConfiguration {

}
