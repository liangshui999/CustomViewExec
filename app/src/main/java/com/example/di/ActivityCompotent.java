package com.example.di;

import com.example.activity.DiActivity;

import dagger.Component;

/**
 * 可以输入多个module，也就是说可以有多个依赖提供者
 * 需要哪个依赖的时候，选择相应的提供者即可
 */
@Component(modules = {ActivityModule.class, ApplicationModule.class})
public interface ActivityCompotent {

    /**
     * 这个方法在自动生成的代码里面起到了关键作用
     */
    void inject(DiActivity activity);
}
