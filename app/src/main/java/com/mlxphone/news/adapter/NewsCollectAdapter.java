package com.mlxphone.news.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlxphone.news.R;
import com.mlxphone.news.activity.NewsActivity;
import com.mlxphone.news.entity.NewsInfo;
import com.mlxphone.news.utils.DBUtil;
import com.mlxphone.news.utils.MyOpenHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLXPHONE on 2016/12/6 0006.
 */

public class NewsCollectAdapter extends RecyclerView.Adapter<NewsCollectAdapter.MyViewHolder> {

    List<NewsInfo> newsInfoList=new ArrayList<>();
    Context context;

    public NewsCollectAdapter(Context context) {
        this.context = context;
    }

    public List<NewsInfo> getNewsInfoList() {
        return newsInfoList;
    }

    public void setNewsInfoList(List<NewsInfo> newsInfoList) {
        this.newsInfoList = newsInfoList;
    }

    /**
     * RecyclerView 中ViewHolder继承父类
     */
    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_news;
        TextView tv_title;
        TextView tv_type;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_news= (ImageView) itemView.findViewById(R.id.iv_news);
            tv_title= (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_type= (TextView) itemView.findViewById(R.id.tv_news_type);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,null);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsInfo newsInfo=newsInfoList.get(position);
        final int i=position;
        View view=holder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsActivity.class);
                String url = newsInfo.getUrl();
                String largeImageUrl = newsInfo.getLargeImageUrl();
                String title = newsInfo.getTitle();
                intent.putExtra("url", url);
                intent.putExtra("largeImageUrl", largeImageUrl);
                intent.putExtra("title", title);
                context.startActivity(intent);

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog alertDialog=new AlertDialog.Builder(context)
                        .setTitle("确定取消收藏吗？")
                        .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MyOpenHelper myOpenHelper=new MyOpenHelper(context,"News.db",null,1);
                                SQLiteDatabase database=myOpenHelper.getReadableDatabase();
                                DBUtil.delete(database,newsInfo);
                                newsInfoList.remove(newsInfo);
                                notifyItemRemoved(i);
                                notifyItemRangeRemoved(i,newsInfoList.size());
                            }
                        }
                )
                        .setNegativeButton("取消",null)
                        .create();
                alertDialog.show();
                return true;
            }
        });
        Picasso.with(context)
                .load(newsInfo.getTargetURL())
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.shape_button_red)
                .into(holder.iv_news);
        holder.tv_type.setText(newsInfo.getType());
        holder.tv_title.setText(newsInfo.getTitle());
    }

    @Override
    public int getItemCount() {
        return newsInfoList.size();
    }




}
