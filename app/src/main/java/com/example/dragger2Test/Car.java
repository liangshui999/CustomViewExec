package com.example.dragger2Test;

import com.example.bean.Engine;
import com.example.bean.Seat;

import javax.inject.Inject;

/**
 * 创建日期：2018-03-29 on 15:49
 * 作者：ls
 */

public class Car {

    @Inject
    Engine engine;

    @Inject
    Seat seat;

    public Car(){
        DaggerCarComponent
                .builder()
                //.carModule(new CarModule())
                .build()
                .inject(this);
    }

    public Engine getEngine() {
        return engine;
    }

    public Seat getSeat() {
        return seat;
    }
}
