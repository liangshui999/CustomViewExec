package com.example.bean;

import com.example.util.MyLog;

import javax.inject.Inject;

/**
 * 创建日期：2018-03-29 on 15:45
 * 作者：ls
 */

public class Seat {

    private Leather leather;

    /*public Seat(){
        MyLog.log("Seat.........");
    }*/


    /**
     * dagger框架会自动去寻找Leather的依赖并注入,所以说Leather必须有依赖的提供者，
     * 也就是说或者在构造函数上面加上@Inject注解，或者专门提供一个module
     */
    @Inject
    public Seat(Leather leather){
        MyLog.log("注入带参数的Seat.......leather = " + leather);
        this.leather = leather;
    }



}
