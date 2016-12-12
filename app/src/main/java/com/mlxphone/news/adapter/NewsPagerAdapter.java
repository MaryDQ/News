package com.mlxphone.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mlxphone.news.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLXPHONE on 2016/12/9 0009.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {
    List<NewsFragment> newsFragmentList=new ArrayList<>();

    public List<NewsFragment> getNewsFragmentList() {
        return newsFragmentList;
    }

    public void setNewsFragmentList(List<NewsFragment> newsFragmentList) {
        this.newsFragmentList = newsFragmentList;
    }

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return newsFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return newsFragmentList.size();
    }
}
