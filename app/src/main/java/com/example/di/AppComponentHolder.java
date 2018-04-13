package com.example.di;

public class AppComponentHolder {

    private static AppCompontent appCompontent;

    public static AppCompontent getAppCompontent() {
        return appCompontent;
    }

    public static void setAppCompontent(AppCompontent appCompontent) {
        AppComponentHolder.appCompontent = appCompontent;
    }
}
