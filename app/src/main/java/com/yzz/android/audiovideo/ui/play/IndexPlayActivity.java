package com.yzz.android.audiovideo.ui.play;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.adapter.LoaclMusicListAdapter;
import com.yzz.android.audiovideo.bean.Musicer;
import com.yzz.android.audiovideo.config.Config;
import com.yzz.android.audiovideo.db.DbHelper;
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
    private List<Musicer> musics;
    private LoaclMusicListAdapter adapter;
    private Intent userIntent;
    private MusicChengeReceiver musicChengeReceiver;
    private SoftReference<IMusicChegeListener> iMusicChegeListener;
    private String musicName;
    private DbHelper<Musicer> dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void setContentView(Bundle savedInstanceState) {
        root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_index_play, null);
        setContentView(root);
        YzzAnn<IndexPlayActivity> yzzAnn = new YzzAnn<>();
        yzzAnn.bind(this);
        musics = new ArrayList<>();
        dbHelper = new DbHelper<>(this, new Musicer(), "yzz");
        Cursor c = dbHelper.select();
        if (c == null) return;
        while (c.moveToNext()) {
            Musicer m = new Musicer();
            m.setPath(c.getString(c.getColumnIndex("path")));
            m.setMusictitle(c.getString(c.getColumnIndex("musictitle")));
            m.setAuthor(c.getString(c.getColumnIndex("author")));
            m.setAlbum(c.getString(c.getColumnIndex("album")));
            m.setYear(c.getString(c.getColumnIndex("year")));
            m.setMemo(c.getString(c.getColumnIndex("memo")));
            m.setRetain(c.getString(c.getColumnIndex("retain")));
            m.setTrack(c.getString(c.getColumnIndex("track")));
            m.setType(c.getString(c.getColumnIndex("type")));
            musics.add(m);
        }
    }

    public void startMusicServer() {
        intent = new Intent(this, MusicPlayServer.class);
        intent.putParcelableArrayListExtra(MusicPlayServer.MUSIC_LIST, (ArrayList<? extends Parcelable>) musics);
        startService(intent);
    }

    @Override
    public void init() {
        userIntent = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
        intentReceiver = new Intent(this, MusicPlayServer.MusicInfoReceiver.class);
        adapter = new LoaclMusicListAdapter(this);
        musicList.setAdapter(adapter);
        adapter.setMusics(musics);
        player.setSelectListener(this);
        if (musics.size() <= 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.getAllMusic(musics);
                    startMusicServer();
                    int size = musics.size();
                    for (int i = 0; i < size; i++) {
                        dbHelper.insert(musics.get(i));
                    }
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
        } else {
            //这里是要初始化listVIew
            adapter.notifyDataSetChanged();
            startMusicServer();
            initBottom();
        }
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userIntent.setAction(MusicPlayServer.PLAY_MUSIC_BY_USER);
                userIntent.putExtra(MusicPlayServer.POSITION, position);
                player.setSelect(true);
                musicNameTv.setText(musics.get(position).getMusictitle());
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
        if (musics.size() > 0)
            musicNameTv.setText(musics.get(0).getMusictitle());
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
        musicName = name;
        if (!TextUtils.isEmpty(name)) {
            musicNameTv.setText("" + name);
            userNameTv.setText("" + author);
        }
    }


    public void goMusicPlayInfo(View view) {
        Intent intent = new Intent(this, PlayMusicActivity.class);
        intent.putExtra(Config.MUSIC_TITLE, musicName);
        intent.putParcelableArrayListExtra(Config.MUSIC_LIST, (ArrayList<? extends Parcelable>) musics);
        startActivity(intent);
    }
}
