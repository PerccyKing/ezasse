package cn.com.pism.ezasse.starter.annotation;

import cn.com.pism.ezasse.starter.EzasseAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 11:08
 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EzasseAutoConfiguration.class)
@Documented
public @interface EnableEzasse {
}
