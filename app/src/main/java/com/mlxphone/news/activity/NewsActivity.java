package com.mlxphone.news.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.mlxphone.news.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsActivity extends AppCompatActivity {

    @InjectView(R.id.wv_news)
    WebView wv_news;
    @InjectView(R.id.iv_news_title)
    ImageView iv_news_title;
    String url;
    String title;
    String largeImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
        initSystemUI();
        wv_news.getSettings().setJavaScriptEnabled(true);//设置可运行javaScript脚本
        wv_news.setWebViewClient(new WebViewClient());
        wv_news.setWebChromeClient(new WebChromeClient());
        wv_news.loadUrl(url);
    }

    private void initSystemUI(){
        //取出新闻数据
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        largeImageUrl=getIntent().getStringExtra("largeImageUrl");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showShare();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this)
                .load(largeImageUrl)
                .into(iv_news_title);
        /*iv_news_title.setTag(largeImageUrl);
        BitmapUtil.setBitmap(this,largeImageUrl,iv_news_title);*/
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("每日新闻");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        oks.setImageUrl(largeImageUrl);
        // 启动分享GUI
        oks.show(this);
    }
}
