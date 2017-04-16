package com.yzz.android.audiovideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.reflect.YzzAnn;
import com.yzz.android.audiovideo.reflect.YzzAnnotation;

import java.util.List;

/**
 * Created by yzz
 * 2017/4/16 0016.
 */
public class LoaclMusicListAdapter extends ABaseAdapter<LoaclMusicListAdapter.Holder> {
    private List<String> musics;
    private Context context;

    public LoaclMusicListAdapter(Context context) {
        this.context = context;
    }

    public void setMusics(List<String> musics) {
        this.musics = musics;
    }


    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loacal_music_lv, null);
        return view;
    }

    @Override
    protected void bindData(Holder holder,int position) {
        holder.nameTv.setText(musics.get(position));
    }

    @Override
    protected Holder initHolder(View contentView) {
        return new Holder(contentView);
    }

    @Override
    public int getCount() {
        return musics == null ? 0 : musics.size();
    }

    @Override
    public Object getItem(int position) {
        return musics.get(position);
    }

    class Holder extends ABaseAdapter.ViewHolder {
        @YzzAnnotation(id = R.id.item_local_music_name_tv)
        private TextView nameTv;
        @YzzAnnotation(id = R.id.item_local_music_user_tv)
        private TextView userTv;
        private YzzAnn<Holder> yzzAnn;
        public Holder(View view) {
            super(view);
            yzzAnn = new YzzAnn();
            yzzAnn.bind(this,view);
        }
    }
}
