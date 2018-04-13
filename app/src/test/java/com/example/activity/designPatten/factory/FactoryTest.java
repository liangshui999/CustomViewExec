package com.example.activity.designPatten.factory;

import org.junit.Test;

/**
 * 创建日期：2018-03-28 on 10:28
 * 作者：ls
 */

public class FactoryTest {

    @Test
    public void test1(){
        AbsCarFactory factory = new CarFactory();
        AbsCar qqCar = factory.createCar(QQCar.class);
        AbsCar audioCar = factory.createCar(AudioCar.class);
        //qqCar.drive();
        audioCar.drive();

    }

}
