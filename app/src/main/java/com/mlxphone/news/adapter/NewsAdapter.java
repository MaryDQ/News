package com.mlxphone.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlxphone.news.R;
import com.mlxphone.news.base.MyBaseAdapter;
import com.mlxphone.news.entity.NewsInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by MLXPHONE on 2016/11/30 0030.
 */

public class NewsAdapter extends MyBaseAdapter<NewsInfo> {
    public NewsAdapter(Context context) {
        super(context);
    }
    public static boolean isFlying=false;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.item_news,null);
            holder=new ViewHolder();
            holder.tv_news_title= (TextView) view.findViewById(R.id.tv_news_title);
            holder.tv_news_type= (TextView) view.findViewById(R.id.tv_news_type);
            holder.iv_news= (ImageView) view.findViewById(R.id.iv_news);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }

//        holder.iv_news.setTag(getItem(i).getTargetURL());
//        holder.iv_news.setImageResource(R.drawable.ic_launcher);
//        BitmapUtil.setBitmap(context,getItem(i).getTargetURL(),holder.iv_news);

        Picasso.with(context)
                .load(getItem(i).getTargetURL())
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.shape_button_red)
                .into(holder.iv_news);

        holder.tv_news_title.setText(getItem(i).getTitle());
        holder.tv_news_type.setText(getItem(i).getType());
        return view;
    }

    static class ViewHolder{
        ImageView iv_news;
        TextView tv_news_title;
        TextView tv_news_type;
    }


}
