package cn.com.pism.ezasses.starter.annotation;

import cn.com.pism.ezasses.starter.EzasseConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/10 下午 11:08
 * @since 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EzasseConfiguration.class)
@Documented
public @interface EnableEzasse {
}
