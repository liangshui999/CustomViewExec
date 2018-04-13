package com.example.dragger2Test.subComponent;

import dagger.Component;

/**
 * 被依赖的component
 */
@Component(modules = {AModule.class})
public interface AComponent {

    //对外暴露A1，依赖该component的component能获取到A1
    //因为没有暴露A2，所以依赖该component的component获取不到A2
    A1 getA1();

    A2 getA2();

}
