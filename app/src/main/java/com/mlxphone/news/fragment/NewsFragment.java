package com.mlxphone.news.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mlxphone.news.R;
import com.mlxphone.news.activity.NewsActivity;
import com.mlxphone.news.adapter.NewsAdapter;
import com.mlxphone.news.entity.NewsInfo;
import com.mlxphone.news.entity.NewsOfjuhe;
import com.mlxphone.news.utils.DBUtil;
import com.mlxphone.news.utils.HttpClientUtil;
import com.mlxphone.news.utils.MyOpenHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by MLXPHONE on 2016/12/1 0001.
 */

public class NewsFragment extends Fragment {
    public String type="top";
    View view = null;
    Context context;
    @InjectView(R.id.lv_home)
    ListView lv_home;
    List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
    NewsAdapter newsAdapter;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what) {
                case 0:
                    newsAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "请求异常", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }

    });

    public NewsFragment() {
    }

    @SuppressLint("ValidFragment")
    public NewsFragment(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        newsAdapter = new NewsAdapter(context);
        newsAdapter.setData(newsInfoList);
        lv_home.setAdapter(newsAdapter);
        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_FLING:
                        newsAdapter.isFlying = true;
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        newsAdapter.isFlying = false;
                        break;
                    case SCROLL_STATE_IDLE:
                        newsAdapter.isFlying = false;
                        newsAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View iv_news = view.findViewById(R.id.iv_news);
                Intent intent = new Intent(context, NewsActivity.class);
                NewsInfo newsInfo = newsAdapter.getItem(i);
                String url = newsInfo.getUrl();
                String largeImageUrl = newsInfo.getLargeImageUrl();
                String title = newsInfo.getTitle();
                intent.putExtra("url", url);
                intent.putExtra("largeImageUrl", largeImageUrl);
                intent.putExtra("title", title);
                if (Build.VERSION.SDK_INT >= 21) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), iv_news, "news").toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        lv_home.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final PopupWindow popupWindow = new PopupWindow();
                View popupView = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow, null);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(popupView);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
                popupWindow.setOutsideTouchable(false);
                //popupWindow.showAsDropDown(view,50,-50);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyOpenHelper myOpenHelper=new MyOpenHelper(context,"News.db",null,1);
                        SQLiteDatabase database=myOpenHelper.getReadableDatabase();
                        if (DBUtil.isNewsExist(database,newsInfoList.get(i))){
                            Toast.makeText(context, "该新闻已被收藏过", Toast.LENGTH_SHORT).show();
                        }else{
                            DBUtil.insert(database,newsAdapter.getItem(i));
                            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                        popupWindow.dismiss();
                    }
                });
                popupView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                return true;
            }
        });
        initListView();
    }

    private void initListView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://v.juhe.cn/toutiao/index?type="+type+"&key=d728ab4e75e137c4f23aec12ed3ee6cd");
                    HttpClientUtil.getJson(url, onJsonGetListener);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private HttpClientUtil.onJsonGetListener onJsonGetListener = new HttpClientUtil.onJsonGetListener() {
        @Override
        public void jsonGetSuccess(String json) {
            Gson gson = new Gson();

            NewsOfjuhe newsData = gson.fromJson(json, NewsOfjuhe.class);
            if (newsData != null) {
                NewsOfjuhe.Result result = newsData.getResult();
                if (result != null) {
                    List<NewsOfjuhe.Data> dataList = result.getData();
                    if (dataList != null) {
                        for (NewsOfjuhe.Data data : dataList) {
                            String url = data.getUrl();
                            String largeImageUrl = data.getThumbnail_pic_s03();
                            String targetURL = data.getThumbnail_pic_s();
                            String title = data.getTitle();
                            String newsType;
                            if (type.equals("top")){
                                newsType=data.getRealtype();
                            }else{
                                newsType=data.getCategory();
                            }

                            NewsInfo info = new NewsInfo(url, largeImageUrl, targetURL, title, newsType);
                            newsInfoList.add(info);
                        }
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        msg.arg1 = -1;
                        handler.sendMessage(msg);
                    }
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    msg.arg1 = -1;
                    handler.sendMessage(msg);
                }
            } else {
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.arg1 = -1;
                handler.sendMessage(msg);
            }

        }

        @Override
        public void jsonGetFail(int responseCode) {
            Message msg = handler.obtainMessage();
            msg.what = 1;
            handler.sendMessage(msg);
        }

        @Override
        public void jsonGetException(Exception e) {
            Message msg = handler.obtainMessage();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    };
}
