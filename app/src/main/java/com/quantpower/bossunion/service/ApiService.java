package com.quantpower.bossunion.service;

import com.quantpower.bossunion.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ShuLin on 2017/7/30.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("login.ashx/")
    Observable<String> getIsRegister(@Field("v") String v, @Field("lan") String lan, @Field("equipment") String equipment, @Field("method") String method, @Field("userName") String userName);
}
