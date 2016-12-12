package com.mlxphone.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mlxphone.news.R;
import com.mlxphone.news.entity.GirlInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLXPHONE on 2016/12/7 0007.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    List<GirlInfo.ResultsBean> resultsBeanList=new ArrayList<>();
    Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public List<GirlInfo.ResultsBean> getResultsBeanList() {
        return resultsBeanList;
    }

    public void setResultsBeanList(List<GirlInfo.ResultsBean> resultsBeanList) {
        this.resultsBeanList = resultsBeanList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,null);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /*View view=holder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mime="image*//*";
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(resultsBeanList.get(position).getUrl()),mime);
                context.startActivity(intent);
            }
        });*/

        Picasso.with(context)
                .load(resultsBeanList.get(position).getUrl())
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.shape_button_red)
                .into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        return resultsBeanList.size();
    }
}
