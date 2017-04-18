package com.yzz.android.audiovideo.ui.play;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.adapter.LoaclMusicListAdapter;
import com.yzz.android.audiovideo.receiver.IMusicChegeListener;
import com.yzz.android.audiovideo.receiver.MusicChengeReceiver;
import com.yzz.android.audiovideo.reflect.YzzAnn;
import com.yzz.android.audiovideo.reflect.YzzAnnotation;
import com.yzz.android.audiovideo.server.MusicPlayServer;
import com.yzz.android.audiovideo.ui.base.BaseActivity;
import com.yzz.android.audiovideo.ui.base.BaseApplication;
import com.yzz.android.audiovideo.util.FileUtils;
import com.yzz.android.audiovideo.widget.YzzCheckBox;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class IndexPlayActivity extends BaseActivity implements View.OnClickListener, YzzCheckBox.SelecterListener, IMusicChegeListener {
    @YzzAnnotation(id = R.id.index_music_play_cb)
    private YzzCheckBox player;
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
    private MusicChengeReceiver musicChengeReceiver;
    private SoftReference<IMusicChegeListener> iMusicChegeListener;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_index_play);
        YzzAnn<IndexPlayActivity> yzzAnn = new YzzAnn<>();
        yzzAnn.bind(this);
    }

    public void startMusicServer() {
        intent = new Intent(this, MusicPlayServer.class);
        intent.putStringArrayListExtra(MusicPlayServer.MUSIC_LIST, (ArrayList<String>) musics);
        intent.putStringArrayListExtra(MusicPlayServer.MuSIC_NAMES, (ArrayList<String>) musicNames);
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
        adapter.setMusics(musicNames);
        player.setSelectListener(this);
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
                player.setSelect(true);
                musicNameTv.setText(musicNames.get(position));
                sendBroadcast(userIntent);
            }
        });

        IntentFilter filter = new IntentFilter("music_change");
        musicChengeReceiver = new MusicChengeReceiver();
        iMusicChegeListener = new SoftReference<IMusicChegeListener>(this);
        musicChengeReceiver.setMusicChangeListener(iMusicChegeListener);
        registerReceiver(musicChengeReceiver, filter);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().setNeedStartActivity(true);
        musicChengeReceiver.remove(iMusicChegeListener);
        unregisterReceiver(musicChengeReceiver);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent();
            home.setAction(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            BaseApplication.getInstance().setNeedStartActivity(false);
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void clickListener(boolean isSelect) {
        if (isSelect) {
            intentReceiver.setAction(MusicPlayServer.PLAY);
        } else {
            intentReceiver.setAction(MusicPlayServer.PAUSE);
        }
        sendBroadcast(intentReceiver);
    }

    @Override
    public void change(int position, String name, String author, long time) {
        if (!TextUtils.isEmpty(name))
            musicNameTv.setText(name);
    }


    public void goMusicPlayInfo(View view) {
        Intent intent = new Intent(this,PlayMusicActivity.class);
        startActivity(intent);
    }
}
