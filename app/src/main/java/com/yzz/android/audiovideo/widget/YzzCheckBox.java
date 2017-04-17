package com.yzz.android.audiovideo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yzz.android.audiovideo.R;

/**
 * Created by yzz
 * 2017/4/17 0017.
 */
public class YzzCheckBox extends ImageView implements View.OnClickListener {

    private int mUnselect;
    private int mSelect;
    private boolean isSelect = false;
    private SelecterListener selectListener;

    public YzzCheckBox(Context context) {
        this(context, null);
    }

    public YzzCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YzzCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.YzzCheckBox);
        mUnselect = array.getResourceId(R.styleable.YzzCheckBox_unSelect, -1);
        mSelect = array.getResourceId(R.styleable.YzzCheckBox_select, -1);
        isSelect = array.getBoolean(R.styleable.YzzCheckBox_selected, false);
        array.recycle();
        setSrc();
        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        isSelect = !isSelect;
        setSrc();
        selectListener.clickListener(isSelect);
    }

    public void setSrc() {
        if (isSelect) {
            if (mSelect != -1)
                setImageResource(mSelect);
        } else {
            if (mUnselect != -1)
                setImageResource(mUnselect);
        }
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        setSrc();
    }

    public void setSelectListener(SelecterListener selectListener) {
        this.selectListener = selectListener;
    }

    public interface SelecterListener{
        void clickListener(boolean isSelect);
    }
}
