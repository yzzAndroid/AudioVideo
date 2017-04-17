package com.yzz.android.audiovideo.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.ui.base.BaseActivity;
import com.yzz.android.audiovideo.ui.base.BaseApplication;

public class StartControalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_start_controal);
        if (BaseApplication.getInstance().isNeedStartActivity()){
            startActivity(new Intent(this,StartActivity.class));
        }else {
            startActivity(new Intent(this,IndexActivity.class));
        }
        finish();
    }
}
