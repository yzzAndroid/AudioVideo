package com.yzz.android.audiovideo.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.adapter.LoaclMusicListAdapter;
import com.yzz.android.audiovideo.reflect.YzzAnn;
import com.yzz.android.audiovideo.reflect.YzzAnnotation;
import com.yzz.android.audiovideo.server.MusicPlayServer;
import com.yzz.android.audiovideo.ui.base.BaseActivity;
import com.yzz.android.audiovideo.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends BaseActivity implements View.OnClickListener {
    @YzzAnnotation(id = R.id.index_music_play_cb, click = true)
    private CheckBox player;
    @YzzAnnotation(id = R.id.index_lv)
    private ListView musicList;
    @YzzAnnotation(id = R.id.music_name_tv)
    private TextView musicNameTv;
    @YzzAnnotation(id = R.id.music_user_tv)
    private TextView userNameTv;
    private Intent intent;
    private Intent intentReceiver;
    private List<String> musics;
    private List<String> musicNames;
    private LoaclMusicListAdapter adapter;
    private Intent userIntent;
    private boolean isInitMusicComplete = false;


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

    public void startMusicServer() {
        intent = new Intent(this, MusicPlayServer.class);
        intent.putStringArrayListExtra(MusicPlayServer.MUSIC_LIST, (ArrayList<String>) musics);
        startService(intent);
    }

    @Override
    public void init() {
        userIntent = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
        intentReceiver = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
        adapter = new LoaclMusicListAdapter(this);
        musicNames = new ArrayList<>();
        musics = new ArrayList<>();
        musicList.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtils.getAllMusic(musics, musicNames);
                isInitMusicComplete = true;
                startMusicServer();
                musicList.post(new Runnable() {
                    @Override
                    public void run() {
                        //这里是要初始化listVIew
                        adapter.setMusics(musicNames);
                        adapter.notifyDataSetChanged();
                        initBottom();
                    }
                });
            }
        }).start();
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userIntent.setAction(MusicPlayServer.PLAY_MUSIC_BY_USER);
                userIntent.putExtra(MusicPlayServer.POSITION, position);
                player.setChecked(true);
                musicNameTv.setText(musicNames.get(position));
                sendBroadcast(userIntent);
            }
        });
    }

    private void initBottom() {
        if (musicNames.size() > 0)
            musicNameTv.setText(musicNames.get(0));
    }

    @Override
    public void bindData() {

    }


    @Override
    public void onClick(View v) {
        if (isInitMusicComplete && musics == null || musics.size() <= 0) {
            player.setChecked(false);
            return;
        }
        switch (v.getId()) {
            case R.id.index_music_play_cb:
                if (player.isChecked()) {
                    getApplication().setTheme(R.style.night);
                    setTheme(R.style.night);
                    intentReceiver.setAction(MusicPlayServer.PLAY);
                } else {
                    intentReceiver.setAction(MusicPlayServer.PAUSE);
                    getApplication().setTheme(R.style.light);
                    setTheme(R.style.light);
                }
                sendBroadcast(intentReceiver);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
