package com.yzz.android.audiovideo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by yzz
 * 2017/4/16 0016.
 */
public abstract class ABaseAdapter<T extends ABaseAdapter.ViewHolder> extends BaseAdapter {

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T holder;
        if (convertView == null) {
            convertView = initContentView();
            holder = initHolder(convertView);
        } else {
            holder = (T) convertView.getTag();
        }
        bindData(holder, position);
        return convertView;
    }

    protected abstract View initContentView();

    protected abstract void bindData(T holder, int position);

    protected abstract T initHolder(View contentView);

    public class ViewHolder {
        protected View view;

        public ViewHolder(View view) {
            this.view = view;
            view.setTag(this);
        }
    }
}
