package com.yzz.android.audiovideo.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yzz
 * 2017/4/25 0025.
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbAnn {
    String tableName()default "";
    boolean isKey()default false;
    boolean isAutoincrement()default false;
    DbType type()default DbType.TEXT;
    int length() default -1;
}
