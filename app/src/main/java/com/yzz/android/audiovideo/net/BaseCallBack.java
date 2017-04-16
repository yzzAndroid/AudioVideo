package com.yzz.android.audiovideo.net;

import okhttp3.Response;

/**
 * @name OnLnePlayer
 * @class name：com.yzz.android.onlneplayer.net
 * @anthor yzz
 * @Email:yzzandroid@163.com
 * @time 2017/3/26 0026 下午 4:51
 */

public interface BaseCallBack {
    void success(Response response);

    void failure(String msg);
}
