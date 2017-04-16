package com.yzz.android.audiovideo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.ui.base.BaseActivity;

/**
 * Created by yzz
 * 2017/4/15 0015.
 */
public class StartActivity extends AppCompatActivity {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null&&msg.what==1){
                removeMessages(1);
                startActivity(new Intent(StartActivity.this,IndexActivity.class));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        handler.sendEmptyMessageDelayed(1,3000);
    }
}
