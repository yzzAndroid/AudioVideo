package com.yzz.android.audiovideo.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.reflect.YzzAnn;

/**
 * Created by yzz
 * 2017/4/15 0015.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected static int statusColor = Color.parseColor("#c62F2F");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(savedInstanceState);
        initStatus();
        init();
        bindData();
    }

    protected  void initStatus(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View view = findViewById(android.R.id.content);
        view.setPadding(0,getStatusH(),0,0);
    }

    /**
     * 子类要实现该方法
     * @param savedInstanceState
     */
    public abstract void setContentView(Bundle savedInstanceState);

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 绑定数据
     */
    public abstract void bindData();

    public int getStatusH(){
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = getResources().getDimensionPixelSize(height);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
}
