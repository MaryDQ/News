package com.mlxphone.news.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MLXPHONE on 2016/12/6 0006.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    public final static String CREATE_TABLE="create table News(" +
            "url text," +
            "imageUrl text," +
            "largeImageUrl text," +
            "title text," +
            "type text)";

    /**
     *
     * @param context 上下文
     * @param name 数据库名称
     * @param factory 不需要的工厂工具
     * @param version 数据库版本
     */
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
