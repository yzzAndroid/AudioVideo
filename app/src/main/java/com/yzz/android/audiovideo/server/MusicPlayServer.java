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

import com.yzz.android.audiovideo.bean.Musicer;
import com.yzz.android.audiovideo.receiver.MusicChengeReceiver;
import com.yzz.android.audiovideo.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    public static final String MUSIC_LIST = "music_list";
    public static final String PLAY_MUSIC_BY_USER = "play_music_by_user";
    public static final String POSITION = "position";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String TIME = "time";
    public static final String PLAYING = "playing";
    private static int position = 0;
    private static List<Musicer> musicers;
    private  Intent musicChange;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        musicers = new ArrayList<>();
        musicChange = new Intent();
        musicChange.setAction("music_change");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicers.addAll((List)intent.getParcelableArrayListExtra(MUSIC_LIST));
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
            if (position > musicers.size() - 1) {
                position = 0;
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicers.get(position).getPath());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        isNeedPrepar = false;
        mediaPlayer.start();
        musicChange.putExtra(NAME,musicers.get(position).getMusictitle());
        musicChange.putExtra(AUTHOR,musicers.get(position).getAuthor());
        musicChange.putExtra(TIME,0L);
        musicChange.putExtra(POSITION,position);
        musicChange.putExtra(PLAYING,true);
        sendBroadcast(musicChange);
        position++;
    }

    public static class MusicInfoReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            try {
                if (PLAY.equals(action)) {
                    if (musicers == null || musicers.size() == 0) return;
                    if (isNeedPrepar) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(musicers.get(position).getPath());
                        mediaPlayer.prepare();
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }
                }
                if (PAUSE.equals(action)) {
                    mediaPlayer.pause();
                    //musicChange.putExtra(PLAYING,true);
                    //sendBroadcast(musicChange);
                }
                if (PLAY_MUSIC_BY_USER.equals(action)) {
                    position = intent.getIntExtra(POSITION, 0);
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(musicers.get(position).getPath());
                    mediaPlayer.prepare();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
