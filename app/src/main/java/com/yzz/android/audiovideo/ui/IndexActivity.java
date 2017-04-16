package com.yzz.android.audiovideo.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.ui.base.BaseActivity;

public class IndexActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void setContentView(Bundle savedInstanceState, PersistableBundle persistentState) {
        setContentView(R.layout.activity_index);
    }

    @Override
    public void init() {
        startActivity(new Intent(this,Test.class));
    }

    @Override
    public void bindData() {

    }


}
