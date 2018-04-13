package com.example.di;

import com.example.dragger2Test.Car;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    /**
     * 提供依赖的方法
     */
    @Provides
    Car provideCar(){
        return new Car();
    }

}
