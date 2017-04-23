package com.yzz.android.audiovideo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yzz.android.audiovideo.R;
import com.yzz.android.audiovideo.bean.Musicer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yzz
 * 2017/4/23 0023.
 */
public class MusicPlayerAdapter extends PagerAdapter {

    private List<Musicer> musicers;
    private Context context;

    public MusicPlayerAdapter(Context context) {
        this.context = context;
    }

    public void setMusicers(List<Musicer> musicers) {
        this.musicers = musicers;
    }

    @Override
    public int getCount() {
        return musicers == null ? 0 : musicers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_vp, null);
        //CircleImageView circleImageView = (CircleImageView) viewGroup.findViewById(R.id.vp_circle_view);
        container.addView(viewGroup);
        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
