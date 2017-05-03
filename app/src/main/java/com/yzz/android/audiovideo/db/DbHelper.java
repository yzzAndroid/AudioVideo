package com.yzz.android.audiovideo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yzz.android.audiovideo.reflect.DbAnn;
import com.yzz.android.audiovideo.reflect.DbType;
import com.yzz.android.audiovideo.ui.base.BaseApplication;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;

/**
 * Created by yzz
 * 2017/4/25 0025.
 * ORM工具类
 */
public final class DbHelper<T> {
    private String dbName;
    private String tableName;
    private SoftReference<Context> contextSoftReference;
    private T t;
    private Class<? extends T> tClass;
    private SQLiteDatabase database;
    private DbManager manager;


    public DbHelper(Context context, T t, String dbName) {
        contextSoftReference = new SoftReference<Context>(context);
        this.t = t;
        this.dbName = dbName;
        init();
    }


    private void init() {
        try {
            if (t == null) return;
            tClass = (Class<? extends T>) t.getClass();
            Field[] fields = tClass.getFields();
            int size = fields.length;
            DbAnn dbAnnClass = tClass.getAnnotation(DbAnn.class);
            if (dbAnnClass != null) {
                tableName = dbAnnClass.tableName();
            }
            for (int i = 0; i < size; i++) {
                Field f = fields[i];
                DbAnn dbAnn = f.getAnnotation(DbAnn.class);
                if (dbAnn == null) continue;
                break;
            }
            dbName = dbName == null ? "yzz" : dbName;
            tableName = tableName == null ? "t_yzz" : tableName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        create();
    }

    /**
     * 创建数据库
     */
    private void create() {
        if (contextSoftReference.get() == null) return;
        manager = new DbManager(contextSoftReference.get(), dbName, 1);
        database = manager.getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ")
                .append(tableName)
                .append(" ( ");
        Field[] fields = tClass.getFields();
        int size = fields.length;
        for (int i = 0; i < size; i++) {
            Field f = fields[i];
            if (f.getAnnotation(DbAnn.class) == null) continue;
            sb.append(getFieldType(f));
            sb.append(" , ");
        }
        String sql = sb.substring(0, sb.length() - 2);
        sql += " ) ";
        database.execSQL(sql);
    }

    public void insert(T t) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ")
                    .append(tableName)
                    .append(" ( ");
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            Field[] fields = t.getClass().getFields();
            int size = fields.length;
            for (int i = 0; i < size; i++) {
                Field f = fields[i];
                if (f.getAnnotation(DbAnn.class) == null) continue;
                if (f.getAnnotation(DbAnn.class).isKey()) continue;
                sb1.append(f.getName()).append(" , ");

                Object oob = f.get(t);
                if (oob == null) continue;
                if (oob.getClass().equals(int.class)) {
                    sb2.append(oob);
                } else {
                    String str = oob.toString().replaceAll("\"","");
                    sb2.append("\"" + str + "\"").append(" , ");
                }
            }
            sb.append(sb1.substring(0, sb1.length() - 2));
            sb.append(" ) ")
                    .append(" values ")
                    .append(" ( ")
                    .append(sb2.substring(0, sb2.length() - 2))
                    .append(" ) ");
            database.execSQL(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Cursor select() {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(tableName);
        Cursor c = database.rawQuery(sb.toString(), null);
        return c;
    }


    private String getFieldType(Field field) {
        StringBuilder sb = new StringBuilder();
        try {
            String type = "text";
            DbAnn dbAnn = field.getAnnotation(DbAnn.class);
            if (dbAnn != null) {
                DbType dbType = dbAnn.type();
                type = dbType.name();
                int len = dbAnn.length();
                sb.append(field.getName());
                if (len != -1) {
                    type += "(" + len + ") ";
                }
                sb.append("  ").append(type);
                if (dbAnn.isKey()) {
                    if (dbAnn.isAutoincrement())
                        sb.append(" primary key autoincrement ");
                    else sb.append(" primary key ");
                }

                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
