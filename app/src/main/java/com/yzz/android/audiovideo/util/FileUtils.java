package com.yzz.android.audiovideo.util;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;
import android.widget.VideoView;

import com.yzz.android.audiovideo.bean.Musicer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
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

    public static void getAllMusic(List<Musicer> simpleList) {
        getFile(getRoot(), simpleList);
    }


    /**
     * >2Mde 歌曲
     *
     * @param root
     */
    public static void getFile(File root, List<Musicer> musicerList) {
        if (root == null || !root.exists()) return;
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            int length = files.length;
            for (int i = 0; i < length; i++) {
                File f = files[i];
                getFile(f, musicerList);
            }
        } else if (root.getAbsolutePath().endsWith("mp3") && root.length() > 1024 * 1024 * 2) {
            //randomReadMusicBasicInfo(root, musicerList);
            getMusicInfo(root,musicerList);
        }
    }

    /**
     * TAG_V1部分是MP3文件的最后128byte的内容.期中包括的信息有:
     * <p/>
     * 标签头"TAG"    3字节
     * 标题            30字节
     * <p/>
     * 作者            30字节
     * <p/>
     * 专辑            30字节
     * <p/>
     * 出品年份        4字节
     * <p/>
     * 备注信息        28字节
     * <p/>
     * 保留            1字节
     * <p/>
     * 音轨            1字节
     * <p/>
     * 类型            1字节
     *
     * @param file
     */
    private static void randomReadMusicBasicInfo(File file, List<Musicer> musicerList) {
        try {
            if (file == null || musicerList == null) return;
            byte[] bytes = new byte[128];
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            int p = randomAccessFile.read() << 8 + randomAccessFile.read();
            String code = "UTF-8";
            byte[] b = new byte[3];
            randomAccessFile.read(b);
            if (b[0] == -17 && b[1] == -69 && b[2] == -65)
                code = "UTF-8";
            else
                code = "GBK";
            randomAccessFile.seek(file.length() - 128);
            randomAccessFile.read(bytes);
            randomAccessFile.close();
            String tga = new String(bytes, 0, 3, code);
            Musicer musicer = new Musicer();
            musicer.setPath(file.getAbsolutePath());
            musicer.setMusictitle(file.getName());
            Log.e("===============","==========="+tga+code);
           if ("TAG".equalsIgnoreCase(tga)) {
                String musictitle = new String(bytes, 3, 30, code);
               String author = new String(bytes, 33, 30, code);
               String album = new String(bytes, 63, 30, code);
               String year = new String(bytes, 93, 4, code);
               String memo = new String(bytes, 97, 28, code);
               String retain = new String(bytes, 125, 1, code);
               String track = new String(bytes, 126, 1, code);
               String type = new String(bytes, 127, 1, code);
               Log.e("===============","==========="+tga+code+musictitle+"="+author);
               musicer.setMusictitle(musictitle);
                musicer.setAuthor(author);
                musicer.setAlbum(album);
                musicer.setYear(year);
                musicer.setMemo(memo);
                musicer.setRetain(retain);
                musicer.setTrack(track);
                musicer.setType(type);
           }
            musicerList.add(musicer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getMusicInfo(File file, List<Musicer> musicerList){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try
        {
            Musicer musicer = new Musicer();
            musicer.setPath(file.getAbsolutePath());
            musicer.setMusictitle(file.getName());
            mmr.setDataSource(file.getAbsolutePath());
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
            byte[] pic = mmr.getEmbeddedPicture();  // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图

            musicer.setMusictitle(title);
            musicer.setAuthor(artist);
            musicer.setAlbum(album);
//            musicer.setYear(year);
//            musicer.setMemo(memo);
//            musicer.setRetain(retain);
//            musicer.setTrack(track);
//            musicer.setType(type);
            musicerList.add(musicer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

