package com.mlxphone.news.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLXPHONE on 2016/11/30 0030.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> dataList=new ArrayList<T>();
    public LayoutInflater inflater;
    public Context context;

    public MyBaseAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    public void setData(List<T> dataList){

        this.dataList=dataList;
    }
    public List<T> getDataList(){
        return dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
