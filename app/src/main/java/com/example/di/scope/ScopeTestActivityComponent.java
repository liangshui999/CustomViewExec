package com.example.di.scope;

import com.example.activity.ScopeTestActivity;

import dagger.Component;

@MyScopeA
@Component(modules = {ScopeTestActivityModule.class})
public interface ScopeTestActivityComponent {

    /**
     * 该方法很重要，完成了提供者向依赖者的注入
     */
    void inject(ScopeTestActivity activity);
}
