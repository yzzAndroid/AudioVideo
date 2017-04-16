package com.yzz.android.audiovideo.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yzz.android.audiovideo.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by yzz
 * 2017/4/16 0016.
 */
public class MusicPlayServer extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static MediaPlayer mediaPlayer;
    private static boolean isNeedPrepar = true;
    public static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PLAY = "play";
    public static final String PAUSE = "pause";
    public static final String NEXT = "next";
    public static final String LAST = "last";
    public static final String MUSIC_PATH = "music_path";
    private static List<String> list;
    private static int position = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = FileUtils.getAllMusic();
            }
        }).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 完成播放
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        //TODO 下一曲
        try {
            if (position > list.size() - 1) {
                position = 0;
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(list.get(position));
            mediaPlayer.prepare();
            position++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        isNeedPrepar = false;
        mediaPlayer.start();
    }

    public static class MusicInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            try {
                if (PLAY.equals(action)) {
                    if (list == null || list.size() == 0) return;
                    if (isNeedPrepar) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(list.get(position));
                        mediaPlayer.prepare();
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }
                }
                if (PAUSE.equals(action)) {
                    mediaPlayer.pause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}