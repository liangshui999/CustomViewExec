package com.example.retrofitTest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建日期：2018-03-27 on 16:34
 * 作者：ls
 */

public class MyHttpClient {

    private static Retrofit retrofit;

    private MyHttpClient(){}

    static{
        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder
                .baseUrl(BaseUrlFactory.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

}
