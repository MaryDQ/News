package com.mlxphone.news.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by MLXPHONE on 2016/12/8 0008.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
