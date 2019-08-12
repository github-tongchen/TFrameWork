package com.tongchen.twatcher.base.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by TongChen at 14:56 on 2019/08/12.
 * <p>
 * Description: 用于标志解析数据类型的注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseFormat {

    String JSON = "json";

    String STRING = "string";

    String XML = "xml";

    String value() default "";
}
