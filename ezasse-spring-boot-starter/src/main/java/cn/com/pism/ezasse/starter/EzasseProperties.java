package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.model.EzasseConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/10 下午 11:09
 * @since 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "spring.ezasse")
@Data
@Component
public class EzasseProperties extends EzasseConfig{
}
