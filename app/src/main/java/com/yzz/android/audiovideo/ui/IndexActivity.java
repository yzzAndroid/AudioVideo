package com.yzz.android.audiovideo.ui;
import android.content.ComponentName;
import android.content.Intent;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.reflect.YzzAnn;
import com.yzz.android.audiovideo.reflect.YzzAnnotation;
import com.yzz.android.audiovideo.server.MusicPlayServer;
import com.yzz.android.audiovideo.ui.base.BaseActivity;

public class IndexActivity extends BaseActivity implements View.OnClickListener{
    @YzzAnnotation(id = R.id.index_music_play_cb,click = true)
    private CheckBox player;
    private Intent intent;
    private Intent intentReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(Bundle savedInstanceState) {
       setContentView(R.layout.activity_index);
        YzzAnn<IndexActivity> yzzAnn = new YzzAnn<>();
        yzzAnn.bind(this);
    }

    @Override
    public void init() {
        intent = new Intent(this, MusicPlayServer.class);
        intentReceiver = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
        startService(intent);

    }

    @Override
    public void bindData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.index_music_play_cb:
                if (player.isChecked()){
                    intentReceiver.setAction(MusicPlayServer.PLAY);
                }else intentReceiver.setAction(MusicPlayServer.PAUSE);
                sendBroadcast(intentReceiver);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
