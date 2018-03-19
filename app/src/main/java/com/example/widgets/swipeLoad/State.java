package com.example.widgets.swipeLoad;

/**
 * 创建日期：2018-03-16 on 10:36
 * 作者：ls
 * 上拉加载和下拉刷新过程中的状态
 */
public enum  State {
    PULL_TO_REFRESH, //下拉刷新
    RELEASE_TO_REFRESH, //释放刷新
    REFRESHING, //正在刷新
    PULL_TO_LOAD, //上拉加载
    RELEASE_TO_LOAD, //释放加载
    LOADING, //正在加载
    DEFAULT //默认状态
}
