package com.shooter.springboot.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Excel自定义注解列
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
	String value() default "";
}
