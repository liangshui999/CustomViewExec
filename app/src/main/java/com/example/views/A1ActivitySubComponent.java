package com.example.views;

import com.example.dragger2Test.subComponent.A1;
import com.example.dragger2Test.subComponent.B1;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class, A1ActivitySubComponent.SubModule.class})
public interface A1ActivitySubComponent extends AndroidInjector<A1Activity>{

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<A1Activity>{}

    /**
     * 在该内部类中提供的依赖都是有效的依赖，
     * dagger-android框架会在需要的地方，帮我们自动完成注入
     * 类似于在spring的配置文件里面进行了配置
     */
    @Module
    class SubModule{

        @Provides
        A1 provideA1(){
          return new A1();
        }

        @Provides
        B1 provideB1(A1 a1){
            return new B1(a1);
        }
    }

}
