package com.example.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.DaggerMyAppComponent;
import com.example.MyAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class MyApplication extends Application implements HasActivityInjector{

    private static Context context;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DaggerMyAppComponent
                .builder()
                .build()
                .inject(this);
    }

    public static Context getContext(){
        return context;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
