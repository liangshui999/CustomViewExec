package com.example.activity.designPatten.factory;

/**
 * 创建日期：2018-03-28 on 10:22
 * 作者：ls
 */

public abstract class AbsCarFactory {

    public abstract <T extends AbsCar> AbsCar createCar(Class<T> clz);

}
