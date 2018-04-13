package com.example.rxjavaTest;

import com.example.util.MyLog;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建日期：2018-03-28 on 14:52
 * 作者：ls
 */

public class BackPressureTest {

    @Test
    public void test1(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for(int i = 0; i < Integer.MAX_VALUE; i++){
                    e.onNext(i);
                    MyLog.log("subscribe: " + i);
                }
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(Schedulers.newThread())
          .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                sleep(5000);
                MyLog.log("accept:  " + integer);
            }
        });
        sleep(50000);
    }

    @Test
    public void test2(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {    //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(2000);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        sleep(50000);
    }

    @Test
    public void test3(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for(int i = 0; ; i++){
                    e.onNext(i);
                    //MyLog.log("subscribe : " + i);
                }
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                MyLog.log("subscribe   a ");
            }
        }).subscribeOn(Schedulers.io());


        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer i, String s) throws Exception {
                return i + s;
            }
        }).observeOn(Schedulers.newThread())
          .subscribe(new Consumer<String>() {
              @Override
              public void accept(String s) throws Exception {
                  MyLog.log("接收到的内容 ：" + s);
              }
          }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                  throwable.printStackTrace();
              }
          });

        sleep(30000);
    }

    @Test
    public void test4(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(2);
            }

            @Override
            public void onNext(Integer i) {
                MyLog.log("i = " + i);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                MyLog.log("onComplete........");
            }
        });
    }

    @Test
    public void test5(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for(int i = 0; i < 129; i++){
                    e.onNext(i);
                    sleep(1000);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer i) {
                        MyLog.log("i = " + i);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        sleep(5000);
    }

    @Test
    public void test6(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for(int i = 0; ; i++){
                    e.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
        sleep(400000);
    }

    @Test
    public void test7(){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                File file = new File("src/test/1.txt");
                MyLog.log("file = " + file.getAbsolutePath());
                FileInputStream fis = null;
                BufferedReader br = null;
                try{
                    fis = new FileInputStream(file);
                    br = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    while((line = br.readLine()) != null){
                        while(e.requested() == 0){
                            MyLog.log("下游没有接收能力了");
                            sleep(1000);
                        }
                        e.onNext(line);
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                s.request(1);
            }

            @Override
            public void onNext(String s) {
                MyLog.log(s);
                sleep(3000);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        sleep(60000);
    }



    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
