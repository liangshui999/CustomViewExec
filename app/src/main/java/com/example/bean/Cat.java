package com.example.bean;

import com.example.di.scope.MyScopeA;

import javax.inject.Inject;

/**
 * 构造函数提供依赖的时候Scope可以放在类上
 */
@MyScopeA
public class Cat {

    @Inject
    public Cat(){

    }
}
