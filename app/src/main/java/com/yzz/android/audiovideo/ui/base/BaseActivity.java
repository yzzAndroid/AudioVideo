package com.yzz.android.audiovideo.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.yzz.android.audiovideo.util.StatusBarCompat;

/**
 * Created by yzz
 * 2017/4/15 0015.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected static int statusColor = Color.parseColor("#c62F2F");
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(savedInstanceState,persistentState);
        StatusBarCompat.compat(this);
        init();
        bindData();
    }

    /**
     * 子类要实现该方法
     * @param savedInstanceState
     * @param persistentState
     */
    public abstract void setContentView(Bundle savedInstanceState, PersistableBundle persistentState);

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 绑定数据
     */
    public abstract void bindData();

    public static int getStatusColor() {
        return statusColor;
    }
}
