package com.example.dragger2Test;

import com.example.util.MyLog;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 创建日期：2018-03-29 on 16:22
 * 作者：ls
 */
public class CarTest {

    @Test
    public void test1(){
        Car car = new Car();
        MyLog.log(car.getSeat());
        MyLog.log(car.getEngine());
    }

}