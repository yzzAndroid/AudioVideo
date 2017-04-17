package com.yzz.android.audiovideo.ui.base;

import android.app.Application;

/**
 * Created by yzz
 * 2017/4/17 0017.
 */
public class BaseApplication extends Application{

    private boolean isNeedStartActivity;

    private static BaseApplication application;

    public BaseApplication() {

    }

    public static BaseApplication getInstance(){
        if (application!=null){
            return application;
        }else {
            return new BaseApplication();
        }
    }

    public void setNeedStartActivity(boolean needStartActivity) {
        isNeedStartActivity = needStartActivity;
    }

    public boolean isNeedStartActivity() {
        return isNeedStartActivity;
    }
}
