package com.yzz.android.audiovideo.ui.play;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.adapter.MusicPlayerAdapter;
import com.yzz.android.audiovideo.bean.Musicer;
import com.yzz.android.audiovideo.config.Config;
import com.yzz.android.audiovideo.receiver.IMusicChegeListener;
import com.yzz.android.audiovideo.receiver.MusicChengeReceiver;
import com.yzz.android.audiovideo.reflect.YzzAnn;
import com.yzz.android.audiovideo.reflect.YzzAnnotation;
import com.yzz.android.audiovideo.server.MusicPlayServer;
import com.yzz.android.audiovideo.ui.base.BaseActivity;
import com.yzz.android.audiovideo.widget.YzzCheckBox;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * 创建日期：2017/4/18 11:50
 *
 * @author yzz
 */
public class PlayMusicActivity extends BaseActivity implements IMusicChegeListener, YzzCheckBox.SelecterListener {
    @YzzAnnotation(id = R.id.music_title_tv)
    private TextView titleTv;
    @YzzAnnotation(id = R.id.music_play_vp)
    private ViewPager vp;
    @YzzAnnotation(id = R.id.play_pause)
    private YzzCheckBox checkBox;
    private List<Musicer> musicers;
    private String musicName;
    private MusicChengeReceiver musicChengeReceiver;
    private SoftReference<IMusicChegeListener> iMusicChegeListener;
    private Intent intentReceiver;
    private MusicPlayerAdapter playerAdapter;
    private int position = 0;
    private Intent userIntent;
    private boolean isSelect;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_play_music, null);
        setContentView(root);
        YzzAnn<PlayMusicActivity> yzzAnn = new YzzAnn<>();
        yzzAnn.bind(this);
    }


    @Override
    public void init() {
        Intent intent = getIntent();
        musicName = intent.getStringExtra(Config.MUSIC_TITLE);
        musicers = intent.getParcelableArrayListExtra(Config.MUSIC_LIST);
        titleTv.setText(TextUtils.isEmpty(musicName) ? "初始化中" : musicName);
        playerAdapter = new MusicPlayerAdapter(this);
        playerAdapter.setMusicers(musicers);
        vp.setAdapter(playerAdapter);
        vp.setCurrentItem(intent.getIntExtra(MusicPlayServer.POSITION, 0));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                intentReceiver.setAction(MusicPlayServer.PLAY_MUSIC_BY_USER);
                intentReceiver.putExtra(MusicPlayServer.POSITION, position);
                sendBroadcast(intentReceiver);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        checkBox.setSelectListener(this);
        bindReceiver();
    }

    private void bindReceiver() {
        IntentFilter filter = new IntentFilter("music_change");
        musicChengeReceiver = new MusicChengeReceiver();
        iMusicChegeListener = new SoftReference<IMusicChegeListener>(this);
        musicChengeReceiver.setMusicChangeListener(iMusicChegeListener);
        registerReceiver(musicChengeReceiver, filter);
        intentReceiver = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
        userIntent = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
    }

    @Override
    public void bindData() {

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void change(int position, String name, String author, long time) {
        titleTv.setText(name);
        vp.setCurrentItem(position);
        this.position = position;
        checkBox.setSelect(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicChengeReceiver);
    }

    public void last(View view) {
        userIntent.setAction(MusicPlayServer.PLAY_MUSIC_BY_USER);
        int p;
        if (position == 0) {
            p = musicers.size() - 1;
        } else {
            p = position-1;
        }
        userIntent.putExtra(MusicPlayServer.POSITION, p);
        sendBroadcast(userIntent);
    }

    public void next(View view) {
        userIntent.setAction(MusicPlayServer.PLAY_MUSIC_BY_USER);
        int p;
        if (position == musicers.size() - 1) {
            p = 0;
        } else {
            p = position+1;
        }
        userIntent.putExtra(MusicPlayServer.POSITION, p);
        sendBroadcast(userIntent);
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
}
