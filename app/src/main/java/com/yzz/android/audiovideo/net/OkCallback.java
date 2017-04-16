package com.yzz.android.audiovideo.net;

import java.lang.ref.SoftReference;

    /**
     * @name OnLnePlayer
     * @class name：com.yzz.android.onlneplayer.net
     * @anthor yzz
     * @Email:yzzandroid@163.com
     * @time 2017/3/26 0026 下午 4:53
     */
    public class OkCallback {
        private static volatile OkCallback okCallback;
        //防止内存泄漏
        private SoftReference<BaseCallBack> callBackSoftReference;

        private OkCallback() {
        }

        public static synchronized OkCallback getInstance() {
            if (okCallback == null) {
                okCallback = new OkCallback();
            }
            return okCallback;
        }

        public BaseCallBack callback(BaseCallBack callback) {
            callBackSoftReference = new SoftReference<BaseCallBack>(callback);
            return callBackSoftReference.get();
        }

}
