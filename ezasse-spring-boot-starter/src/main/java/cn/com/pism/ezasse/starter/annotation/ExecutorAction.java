package cn.com.pism.ezasse.starter.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author PerccyKing
 * @since 25-03-16 11:15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface ExecutorAction {

    @AliasFor("dataSourceType")
    String[] value() default {};

    @AliasFor("value")
    String[] dataSourceType() default {};
}
