package com.mlxphone.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mlxphone.news.R;
import com.mlxphone.news.adapter.ImageAdapter;
import com.mlxphone.news.entity.GirlInfo;
import com.mlxphone.news.interfaces.GirlsInterface;
import com.orhanobut.logger.Logger;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MLXPHONE on 2016/12/1 0001.
 */

public class ImageFragment extends Fragment {
    private static final String TAG = "ImageFragment";
    @InjectView(R.id.rv_image)
    RecyclerView rv_image;
    List<GirlInfo.ResultsBean> resultsBeanList=new ArrayList<>();
    Context context;
    ImageAdapter imageAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_image,container,false);
        ButterKnife.inject(this,view);
        context=getActivity();
        imageAdapter=new ImageAdapter(context);
        imageAdapter.setResultsBeanList(resultsBeanList);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
        rv_image.setLayoutManager(staggeredGridLayoutManager);
        rv_image.setAdapter(imageAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getJson();
        Logger.d("12341234");
        //getxUtilsJson();
    }

    public void getxUtilsJson(){
        RequestParams params=new RequestParams("http://gank.io/api/data/福利/30/5");
        //params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.d(result.toString());
                System.out.println("-------------------------------------------------"+result.toString());
                Gson gson=new Gson();
                GirlInfo girlInfo=gson.fromJson(result,GirlInfo.class);
                resultsBeanList=girlInfo.getResults();
                imageAdapter.setResultsBeanList(resultsBeanList);
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logger.d("获取信息错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Logger.d("取消获取");
            }

            @Override
            public void onFinished() {
                Logger.d("获取完成");
            }
        });

    }

    public void getJson(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GirlsInterface girlsInterface=retrofit.create(GirlsInterface.class);
        Call<GirlInfo> call=girlsInterface.getGirls();
        call.enqueue(new Callback<GirlInfo>() {
            @Override
            public void onResponse(Call<GirlInfo> call, Response<GirlInfo> response) {
                GirlInfo girlInfo=response.body();
                resultsBeanList=girlInfo.getResults();
                imageAdapter.setResultsBeanList(resultsBeanList);
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GirlInfo> call, Throwable t) {

            }
        });
    }
}
