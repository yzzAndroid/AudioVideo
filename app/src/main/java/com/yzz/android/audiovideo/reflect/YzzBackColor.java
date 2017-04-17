package com.yzz.android.audiovideo.reflect;

import android.graphics.Color;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yzz
 * 2017/4/17 0017.
 */
//作用域
@Target({ElementType.FIELD})
//生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface YzzBackColor {
    int lightColor();
    int nightColor();
}

