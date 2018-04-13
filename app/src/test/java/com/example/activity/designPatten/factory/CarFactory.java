package com.example.activity.designPatten.factory;

/**
 * 创建日期：2018-03-28 on 10:26
 * 作者：ls
 */

public class CarFactory extends AbsCarFactory {
    @Override
    public <T extends AbsCar> AbsCar createCar(Class<T> clz) {
        try {
            return (AbsCar) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
