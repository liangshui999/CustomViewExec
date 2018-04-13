package com.example.di.scope;

import com.example.dragger2Test.Car;

import dagger.Module;
import dagger.Provides;

@Module
public class ScopeTestActivityModule {

    /**
     * MyScopeA注解用于实现所谓的局部单例
     */
    @MyScopeA
    @Provides
    Car provideCar(){
        return new Car();
    }
}
