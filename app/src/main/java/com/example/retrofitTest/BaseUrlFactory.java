package com.example.retrofitTest;

/**
 * 创建日期：2018-03-27 on 16:36
 * 作者：ls
 */

public class BaseUrlFactory {

    private static String baseUrl = "http://www.kuaidi100.com/";

    private BaseUrlFactory(){}

    public static String getBaseUrl(){
        return baseUrl;
    }

}
