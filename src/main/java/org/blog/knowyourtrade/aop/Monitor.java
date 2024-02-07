package org.blog.knowyourtrade.aop;

import org.blog.knowyourtrade.domain.enums.MonitorType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Monitor {

    String value() default "";

    boolean requests() default true;

    boolean timer() default true;

    boolean parameterOutput() default true;

    boolean resultOutput() default true;

    MonitorType monitor() default MonitorType.API;
}

