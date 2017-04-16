package com.yzz.android.audiovideo.net;

import android.net.SSLCertificateSocketFactory;
import android.util.ArrayMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @name OnLnePlayer
 * @class name：com.yzz.android.onlneplayer.net
 * @anthor yzz
 * @Email:yzzandroid@163.com
 * @time 2017/3/26 0026 下午 4:42
 */
public class OkUtils {
    private OkHttpClient okHttpClient;
    private Request.Builder requestBuilder;
    private static OkUtils okUtils;
    public static final int COUNT = 1024;

    private OkUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)
                .build();

        requestBuilder = new Request.Builder()
                .get();
    }

    public static synchronized OkUtils getInstance() {
        if (okUtils == null) {
            okUtils = new OkUtils();
        }
        return okUtils;
    }

    public void doPost(HashMap<String, String> map, String url, final BaseCallBack callBack) {
        if (map == null) return;
        if (callBack == null) return;
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> en : map.entrySet()) {
            bodyBuilder.add(en.getKey(), en.getValue());
        }
        Request request = requestBuilder
                .url(url)
                .post(bodyBuilder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack == null) return;
                if (e == null) return;
                callBack.failure(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack == null) return;
                callBack.success(response);
            }
        });
    }

    public void doGet(String url, final BaseCallBack callBack) {
        if (callBack == null) return;
        Request request = requestBuilder
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack == null) return;
                if (e == null) return;
                callBack.failure(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack == null) return;
                callBack.success(response);
            }
        });

    }
}
