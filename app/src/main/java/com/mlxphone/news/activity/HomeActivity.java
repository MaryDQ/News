package com.mlxphone.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mlxphone.news.R;
import com.mlxphone.news.fragment.ImageFragment;
import com.mlxphone.news.fragment.NewsCollectFragment;
import com.mlxphone.news.fragment.NewsPagerFragment;
import com.orhanobut.logger.Logger;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment currentFragment;
    ImageView iv_user_photo;
    TextView tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSystemUI();
        showNewsFragment();
        ShareSDK.initSDK(this,"157fc72150700");
    }

    private void initSystemUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        iv_user_photo= (ImageView) headerView.findViewById(R.id.iv_user_photo);
        tv_user_name= (TextView) headerView.findViewById(R.id.tv_user_name);
        iv_user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginQQ();
                Toast.makeText(HomeActivity.this, "用户头像", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginQQ(){
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Logger.d(platform.getDb().exportData());
                final String iv_user_url=platform.getDb().getUserIcon();
                final String user_name=platform.getDb().getUserName();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageOptions imageOptions=new ImageOptions.Builder().setCircular(true).build();
                        x.image().bind(iv_user_photo,iv_user_url,imageOptions);
                        tv_user_name.setText(user_name);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qq.authorize();
        qq.showUser(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
    
    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            showNewsFragment();
        } else if (id == R.id.nav_gallery) {
            showImageFragment();
        } else if (id == R.id.nav_slideshow) {
            showCollectFragment();
        } else if (id == R.id.nav_manage) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            showShare();
        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNewsFragment() {
        if (currentFragment instanceof NewsPagerFragment) {

        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            NewsPagerFragment npf = new NewsPagerFragment();
            ft.replace(R.id.fragment_main, npf);
            ft.commit();
            currentFragment = npf;
        }

    }

    private void showImageFragment() {
        if (currentFragment instanceof ImageFragment) {

        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ImageFragment imf = new ImageFragment();
            ft.replace(R.id.fragment_main, imf);
            ft.commit();
            currentFragment = imf;
        }

    }

    private void showCollectFragment(){
        if (currentFragment instanceof NewsCollectFragment) {

        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            NewsCollectFragment ncf = new NewsCollectFragment();
            ft.replace(R.id.fragment_main, ncf);
            ft.commit();
            currentFragment = ncf;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("每日新闻");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://www.baidu.com/s?wd=NBA");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("基于Android6.0平台的新闻客户端");
        // 启动分享GUI
        oks.show(this);
    }

}
