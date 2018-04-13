package com.example.dragger2Test;

import dagger.Component;

/**
 * 创建日期：2018-03-29 on 16:08
 * 作者：ls
 */

@Component(modules = {CarModule.class})
public interface CarComponent {

    void inject(Car car);

}
