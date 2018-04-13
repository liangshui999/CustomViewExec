package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.dragger2Test.Car;

import javax.inject.Inject;

public class DiActivity extends Activity {

    private static final String TAG = "DiActivity";

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Car car;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DaggerActivityCompotent.builder().build().inject(this);
        Log.d(TAG, "context = " + "......car = " + car + "sharedPreferences = " + sharedPreferences);

    }
}
