package com.mlxphone.news.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mlxphone.news.entity.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLXPHONE on 2016/12/6 0006.
 */

public class DBUtil {
    public static void insert(SQLiteDatabase database, NewsInfo newsInfo){
        ContentValues values=new ContentValues();
        values.put("url",newsInfo.getUrl());
        values.put("imageUrl",newsInfo.getTargetURL());
        values.put("title",newsInfo.getTitle());
        values.put("type",newsInfo.getType());
        values.put("largeImageUrl",newsInfo.getLargeImageUrl());
        database.insert("News",null,values);
    }

    public static void delete(SQLiteDatabase database, NewsInfo newsInfo){
        String url=newsInfo.getUrl();
        database.delete("News","url=?",new String[]{url});
    }

    public static boolean isNewsExist(SQLiteDatabase database, NewsInfo newsInfo){
        Cursor cursor=database.query("News",null,"url=?",new String[]{newsInfo.getUrl()},null,null,null);
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public static List<NewsInfo> queryAllNews(SQLiteDatabase database){
        List<NewsInfo> newsInfos=new ArrayList<NewsInfo>();
        Cursor cursor=database.query("News",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String url=cursor.getString(cursor.getColumnIndex("url"));
            String largeImageUrl=cursor.getString(cursor.getColumnIndex("largeImageUrl"));
            String type=cursor.getString(cursor.getColumnIndex("type"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String imageUrl=cursor.getString(cursor.getColumnIndex("imageUrl"));
            NewsInfo newsInfo=new NewsInfo(url,largeImageUrl,imageUrl,title,type);
            newsInfos.add(newsInfo);
        }
        cursor.close();
        return newsInfos;
    }
}
