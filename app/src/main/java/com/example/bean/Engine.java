package com.example.bean;

import com.example.util.MyLog;

import javax.inject.Inject;

/**
 * 创建日期：2018-03-29 on 15:44
 * 作者：ls
 */

public class Engine {

    /**
     * 充当依赖提供者的角色
     */
    @Inject
    public Engine(){
        MyLog.log("Engine............");
    }

}
