package com.example.dragger2Test.subComponent;

import dagger.Module;
import dagger.Provides;

@Module
public class AModule {

    @Provides
    public A1 provideA1(){
        return new A1();
    }

    @Provides
    public A2 provideA2(){
        return new A2();
    }

}
