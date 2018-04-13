package com.example.retrofitTest.services;

import com.example.bean.KuaiDiModel;
import com.example.bean.KuaiDiQueryBean;
import com.example.retrofitTest.MyHttpClient;
import com.example.util.MyLog;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * 创建日期：2018-03-27 on 16:48
 * 作者：ls
 */
public class ZenServiceTest {

    @Test
    public void getZen() throws Exception {
        ZenService zenService = MyHttpClient.getRetrofit().create(ZenService.class);
        Call<ResponseBody> call =  zenService.getZen();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.log("call = " + call);
                MyLog.log("response = " + response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        Thread.sleep(3000);
    }


    @Test
    public void testGetUserInfo() throws InterruptedException {
        ZenService zenService = MyHttpClient.getRetrofit().create(ZenService.class);
        Call<ResponseBody> call = zenService.getUserInfo("zhongtong", "484985211036");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    MyLog.log("response = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.log("t = " + t);
            }
        });
        Thread.sleep(3000);
    }



    @Test
    public void testGetKuaiDi() throws InterruptedException {
        ZenService service = MyHttpClient.getRetrofit().create(ZenService.class);
        Call<KuaiDiModel> call = service.getKuaiDi("zhongtong", "484985211036");
        call.enqueue(new Callback<KuaiDiModel>() {
            @Override
            public void onResponse(Call<KuaiDiModel> call, Response<KuaiDiModel> response) {
                KuaiDiModel model = response.body();
                MyLog.log(model);
            }

            @Override
            public void onFailure(Call<KuaiDiModel> call, Throwable t) {
                MyLog.log("t = " + t);
            }
        });

        Thread.sleep(3000);
    }

    @Test
    public void testGetKuaiRx() throws InterruptedException {
        ZenService zenService = MyHttpClient.getRetrofit().create(ZenService.class);
        Observable<KuaiDiModel> observable = zenService.getKuaiDiRx("zhongtong", "484985211036");
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<KuaiDiModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                MyLog.log("onSubscribe......");
            }

            @Override
            public void onNext(KuaiDiModel value) {
                MyLog.log("value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                MyLog.log("onError......");
            }

            @Override
            public void onComplete() {
                MyLog.log("onComplete......");
            }
        });

        Thread.sleep(3000);
    }

    @Test
    public void testGetKuaiDiBody() throws InterruptedException {
        KuaiDiQueryBean bean = new KuaiDiQueryBean();
        bean.setType("zhongtong");
        bean.setPostid("484985211036");
        ZenService service = MyHttpClient.getRetrofit().create(ZenService.class);
        Observable<KuaiDiModel> observable = service.getKuaiDiBody(bean);
        observable.subscribeOn(Schedulers.io()).subscribe(new Consumer<KuaiDiModel>() {
            @Override
            public void accept(KuaiDiModel kuaiDiModel) throws Exception {
                MyLog.log("kuaiDiModel = " + kuaiDiModel);
            }
        });
        Thread.sleep(3000);
    }

}