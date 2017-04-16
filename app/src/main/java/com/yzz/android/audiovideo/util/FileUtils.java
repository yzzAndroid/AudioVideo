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

    public static void getAllMusic(List<String> list, List<String> simpleList ) {
        getFile(getRoot(), list,false);
        getFile(getRoot(),simpleList,true);
    }


    public static void getFile(File root, List<String> list,boolean isSimple) {
        if (root == null || !root.exists()) return;
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            int length = files.length;
            for (int i = 0; i < length; i++) {
                File f = files[i];
                getFile(f, list,isSimple);
            }
        } else if (root.getAbsolutePath().endsWith("mp3")) {
            if (isSimple)list.add(root.getName());
            else list.add(root.getAbsolutePath());
        }
    }
}

