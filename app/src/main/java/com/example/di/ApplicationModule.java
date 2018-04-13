package com.example.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.util.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(){
        this.context = MyApplication.getContext();
    }

    @Provides
    public Context provideContext(){
        return context;
    }

    @Provides
    public SharedPreferences provideSp(){
        return context.getSharedPreferences("qj", Context.MODE_PRIVATE);
    }

}
