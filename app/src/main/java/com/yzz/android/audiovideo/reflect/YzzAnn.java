package com.yzz.android.audiovideo.reflect;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzz on 2017/3/22 0022.
 */
public class YzzAnn<T> {
    private SoftReference<T> softReference;
    private Class<? extends Object> aClass;
    private SoftReference<View> softReferenceView;
    private T t;
    private static List<SoftReference> referenceList;

    static {
        referenceList = new ArrayList<>();
    }

    public static void changeColor(){
        int size = referenceList.size();
        for (int i = 0; i <size ; i++) {

        }
    }

    public YzzAnn() {

    }

    public void bind(T t) {
        if (t == null) return;
        softReference = new SoftReference<>(t);
        if (t instanceof ViewGroup || t instanceof Activity || t instanceof Fragment) {
            reflect(softReference.get());
        }
    }


    public void bind(T t, View view) {
        if (view == null || t == null) return;
        softReferenceView = new SoftReference<>(view);
        this.t = t;
        reflect(t);
    }

    /**
     * 反射获取字段
     */
    private void reflect(T t) {
        if (t == null) {
            throw new RuntimeException("null entity");
        }
        aClass = t.getClass();
        try {
            init(aClass.getDeclaredFields());
            init(aClass.getFields());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 为字段设置id
     *
     * @param f
     * @throws Exception
     */
    public void init(Field[] f) throws Exception {
        if (f == null) return;
        for (Field field : f) {
            if (field.isAnnotationPresent(YzzAnnotation.class)) {
                //这里要
                field.setAccessible(true);
                YzzAnnotation yzz = field.getAnnotation(YzzAnnotation.class);
                SoftReference softReference = null;
                if (this.softReference != null) {
                    softReference = this.softReference;
                } else if (softReferenceView != null) {
                    softReference = softReferenceView;
                }
                Method m = softReference.get().getClass().getMethod("findViewById", int.class);
                Object ob = m.invoke(softReference.get(), yzz.id());
                if (this.softReference != null) {
                    field.set(softReference.get(), ob);
                } else if (t != null) {
                    field.set(t, ob);
                }
                //设置监听
                if (softReference == null) return;
                Class<?> inter[] = aClass.getInterfaces();
                for (Class<?> c : inter) {
                    if (c.getName().equals(View.OnClickListener.class.getName())) {
                        if (yzz.click()) {
                            Method setOnclick = field.getType().getMethod("setOnClickListener", View.OnClickListener.class);
                            setOnclick.invoke(field.get(softReference.get()), softReference.get());
                        }
                    }
                }
            }
        }
    }

}
