package com.yzz.android.audiovideo.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yzz.android.audiovideo.server.MusicPlayServer;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzz
 * 2017/4/17 0017.
 */
public class MusicChengeReceiver extends BroadcastReceiver {
    private List<SoftReference> referenceList;

    public MusicChengeReceiver() {
        referenceList = new ArrayList<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("music_change".equals(action)) {
            int position = intent.getIntExtra(MusicPlayServer.POSITION, 0);
            String name = intent.getStringExtra(MusicPlayServer.NAME);
            String author = intent.getStringExtra(MusicPlayServer.AUTHOR);
            long time = intent.getLongExtra(MusicPlayServer.TIME, 0L);
            int size = referenceList.size();
            for (int i = 0; i < size; i++) {
                if (referenceList.get(i) == null) continue;
                IMusicChegeListener listener = (IMusicChegeListener) referenceList.get(i).get();

                listener.change(position, name, author, time);
            }
        }
    }

    public void setMusicChangeListener(SoftReference<IMusicChegeListener> s) {
        referenceList.add(s);
    }

    public void remove(SoftReference<IMusicChegeListener> s) {
        referenceList.remove(s);
    }

}
