package com.yzz.android.audiovideo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.yzz.android.audiovideo.reflect.DbAnn;
import com.yzz.android.audiovideo.reflect.DbType;

import java.io.Serializable;

/**
 * Created by yzz
 * 2017/4/22 0022.
 */
@DbAnn(tableName = "yzz_music")
public class Musicer implements Parcelable {
    @DbAnn(isKey = true, isAutoincrement = true, type = DbType.INTEGER)
    public int id;
    @DbAnn(type = DbType.VARCHAR, length = 35)
    public String path;
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String musictitle;
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String author = "未知";
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String album = "未知";
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String year = "未知";
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String memo = "未知";
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String retain = "未知";
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String track = "未知";
    @DbAnn(type = DbType.VARCHAR, length = 20)
    public String type = "未知";

    public Musicer(int id, String path, String musictitle, String author, String album, String year, String memo, String retain, String track, String type) {
        this.id = id;
        this.path = path;
        this.musictitle = musictitle;
        this.author = author;
        this.album = album;
        this.year = year;
        this.memo = memo;
        this.retain = retain;
        this.track = track;
        this.type = type;
    }

    public Musicer(int id, String musictitle, String author, String album, String year, String memo, String retain, String track, String type) {
        this.id = id;
        this.musictitle = musictitle;
        this.author = author;
        this.album = album;
        this.year = year;
        this.memo = memo;
        this.retain = retain;
        this.track = track;
        this.type = type;
    }

    public Musicer(String musictitle, String author, String album, String year, String memo, String retain, String track, String type) {
        this.musictitle = musictitle;
        this.author = author;
        this.album = album;
        this.year = year;
        this.memo = memo;
        this.retain = retain;
        this.track = track;
        this.type = type;
    }

    public Musicer() {
    }

    protected Musicer(Parcel in) {
        id = in.readInt();
        path = in.readString();
        musictitle = in.readString();
        author = in.readString();
        album = in.readString();
        year = in.readString();
        memo = in.readString();
        retain = in.readString();
        track = in.readString();
        type = in.readString();
    }

    public static final Creator<Musicer> CREATOR = new Creator<Musicer>() {
        @Override
        public Musicer createFromParcel(Parcel in) {
            Musicer musicer = new Musicer();
            int id = in.readInt();
            String path = in.readString();
            String musictitle = in.readString();
            String author = in.readString();
            String album = in.readString();
            String year = in.readString();
            String memo = in.readString();
            String retain = in.readString();
            String track = in.readString();
            String type = in.readString();
            return new Musicer(id, path, musictitle, author, album, year, memo, retain, track, type);
        }

        @Override
        public Musicer[] newArray(int size) {
            return new Musicer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusictitle() {
        return musictitle;
    }

    public void setMusictitle(String musictitle) {
        this.musictitle = musictitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRetain() {
        return retain;
    }

    public void setRetain(String retain) {
        this.retain = retain;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Musicer musicer = (Musicer) o;

        if (id != musicer.id) return false;
        if (path != null ? !path.equals(musicer.path) : musicer.path != null) return false;
        if (musictitle != null ? !musictitle.equals(musicer.musictitle) : musicer.musictitle != null)
            return false;
        if (author != null ? !author.equals(musicer.author) : musicer.author != null) return false;
        if (album != null ? !album.equals(musicer.album) : musicer.album != null) return false;
        if (year != null ? !year.equals(musicer.year) : musicer.year != null) return false;
        if (memo != null ? !memo.equals(musicer.memo) : musicer.memo != null) return false;
        if (retain != null ? !retain.equals(musicer.retain) : musicer.retain != null) return false;
        if (track != null ? !track.equals(musicer.track) : musicer.track != null) return false;
        return type != null ? type.equals(musicer.type) : musicer.type == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (musictitle != null ? musictitle.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (retain != null ? retain.hashCode() : 0);
        result = 31 * result + (track != null ? track.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Musicer{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", musictitle='" + musictitle + '\'' +
                ", author='" + author + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", memo='" + memo + '\'' +
                ", retain='" + retain + '\'' +
                ", track='" + track + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(musictitle);
        dest.writeString(author);
        dest.writeString(album);
        dest.writeString(year);
        dest.writeString(memo);
        dest.writeString(retain);
        dest.writeString(track);
        dest.writeString(type);
    }
}
