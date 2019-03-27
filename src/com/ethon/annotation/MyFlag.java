package com.ethon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyFlag {
	public String name() default "无名氏";
	public String value() default "测试value";
}
