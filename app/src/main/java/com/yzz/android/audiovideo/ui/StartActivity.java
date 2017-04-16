package com.yzz.android.audiovideo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.ui.base.BaseActivity;

/**
 * Created by yzz
 * 2017/4/15 0015.
 */
public class StartActivity extends BaseActivity {
    Handler handler = new Handler();
    @Override
    public void setContentView(Bundle savedInstanceState, PersistableBundle persistentState) {
        setContentView(R.layout.activity_start);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this,IndexActivity.class));
            }
        }, 3000);
    }

    @Override
    public void bindData() {

    }


}
