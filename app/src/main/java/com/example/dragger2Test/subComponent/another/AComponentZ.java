package com.example.dragger2Test.subComponent.another;

import com.example.dragger2Test.subComponent.AModule;
import com.example.dragger2Test.subComponent.BComponent;
import com.example.dragger2Test.subComponent.BModule;

import dagger.Component;

/**
 * 没有对外暴露任何AModule里面提供的依赖
 */
@Component(modules = {AModule.class})
public interface AComponentZ {
    /**
     * 生成子组件,返回值是子组件
     */
    BSubComponent createBSubComponent();

}
