package com.example;

import com.example.util.MyApplication;
import com.example.views.A1ActivityModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        A1ActivityModule.class
        })
public interface MyAppComponent {

    void inject(MyApplication application);
}
