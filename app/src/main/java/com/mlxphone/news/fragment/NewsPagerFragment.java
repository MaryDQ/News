package com.mlxphone.news.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlxphone.news.R;
import com.mlxphone.news.adapter.NewsPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by MLXPHONE on 2016/12/9 0009.
 */

public class NewsPagerFragment extends Fragment {
    @InjectView(R.id.vp_news_pager)
    ViewPager vp_news_pager;
    @InjectView(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    NewsPagerAdapter adapter;
    String types[]=new String[]{"top","keji","shehui","junshi"};
    String ts[]=new String[]{"头条","科技","社会","军事"};
    List<NewsFragment> newsFragmentList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_pager,container,false);
        ButterKnife.inject(this,view);
        adapter=new NewsPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.setNewsFragmentList(newsFragmentList);
        vp_news_pager.setAdapter(adapter);
        initIndicator();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i=0;i<types.length;i++){
            NewsFragment newsFragment=new NewsFragment(types[i]);
            newsFragmentList.add(newsFragment);
        }
        adapter.setNewsFragmentList(newsFragmentList);
        adapter.notifyDataSetChanged();
    }
    private void initIndicator(){
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return ts.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.WHITE);
                colorTransitionPagerTitleView.setSelectedColor(Color.WHITE);
                colorTransitionPagerTitleView.setText(ts[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_news_pager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#fff000"));
                /*TriangularPagerIndicator indicator=new TriangularPagerIndicator(context);
                indicator.setLineColor(Color.parseColor("#3F51B5"));*/
                return indicator;
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, vp_news_pager);
    }
}
