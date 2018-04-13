package com.example.views;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {A1ActivitySubComponent.class})
public abstract class A1ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(A1Activity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindA1ActivityInjectorFactory(
            A1ActivitySubComponent.Builder builder);
}
