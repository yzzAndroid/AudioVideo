package com.yzz.android.audiovideo.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzz
 * 2017/4/16 0016.
 */
public class FileUtils {

    public static File getRoot() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    public static List<String> getAllMusic() {
        List<String> list = new ArrayList<>();
        getFile(getRoot(), list);
        return list;
    }

    public static void getFile(File root, List<String> list) {
        if (root == null || !root.exists()) return;
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            int length = files.length;
            for (int i = 0; i < length; i++) {
                File f = files[i];
                getFile(f, list);
            }
        } else if (root.getAbsolutePath().endsWith("mp3")) {
            list.add(root.getAbsolutePath());
            Log.e("music", root.getAbsolutePath());
        }
    }
}

