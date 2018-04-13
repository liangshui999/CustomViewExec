package com.example.retrofitTest.services;

import com.example.bean.KuaiDiModel;
import com.example.bean.KuaiDiQueryBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 创建日期：2018-03-27 on 16:42
 * 作者：ls
 */

public interface ZenService {

    @GET("/zen")
    Call<ResponseBody> getZen();

    @GET("query")
    Call<ResponseBody> getUserInfo(@Query("type") String type, @Query("postid") String postId);

    @GET("query")
    Call<KuaiDiModel> getKuaiDi(@Query("type") String type, @Query("postid") String postId);

    @GET("query")
    Observable<KuaiDiModel> getKuaiDiRx(@Query("type") String type, @Query("postid") String postId);

    @POST("query")
    Observable<KuaiDiModel> getKuaiDiBody(@Body KuaiDiQueryBean bean);
}
