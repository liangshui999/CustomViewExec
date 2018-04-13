package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bean.Cat;
import com.example.di.scope.DaggerScopeTestActivityComponent;
import com.example.dragger2Test.Car;

import javax.inject.Inject;

public class ScopeTestActivity extends Activity {
    private static final String TAG = "ScopeTestActivity";

    @Inject
    Car car1;

    @Inject
    Car car2;

    @Inject
    Cat cat1;

    @Inject
    Cat cat2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerScopeTestActivityComponent
                .builder()
                .build()
                .inject(this);
        Log.d(TAG, "car1 = " + car1 + "........." + "car2 = " + car2 );
        Log.d(TAG, "cat1 = " + cat1 + "........" + "cat2 = " + cat2);
    }


}
