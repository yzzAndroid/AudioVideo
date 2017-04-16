package com.yzz.android.audiovideo.net;

import android.util.Log;

/**
 * @name OnLnePlayer
 * @class name：com.yzz.android.onlneplayer.net
 * @anthor yzz
 * @Email:yzzandroid@163.com
 * @time 2017/3/26 0026 下午 5:27
 */
public abstract class BasCallBackImpl implements BaseCallBack {
    public static final String TAG = "ERROR";

    @Override
    public void failure(String msg) {
        Log.e(TAG, msg);
    }
}
