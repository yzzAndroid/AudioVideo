package com.yzz.android.audiovideo.ui.base;

import android.app.Application;

/**
 * Created by yzz
 * 2017/4/17 0017.
 */
public class BaseApplication extends Application{

    private boolean isNeedStartActivity = true;

    private static BaseApplication application = new BaseApplication();

    public BaseApplication() {

    }

    public static BaseApplication getInstance(){
       return application;
    }

    public void setNeedStartActivity(boolean needStartActivity) {
        isNeedStartActivity = needStartActivity;
    }

    public boolean isNeedStartActivity() {
        return isNeedStartActivity;
    }
}
