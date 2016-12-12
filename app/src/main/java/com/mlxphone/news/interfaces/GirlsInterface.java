package com.mlxphone.news.interfaces;

import com.mlxphone.news.entity.GirlInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MLXPHONE on 2016/12/7 0007.
 */

public interface GirlsInterface {

    @GET("api/data/福利/30/5")
    Call<GirlInfo> getGirls();
}
