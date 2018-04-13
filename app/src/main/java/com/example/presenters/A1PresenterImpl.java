package com.example.presenters;

import android.util.Log;

import com.example.constraint.A1Constraint;
import com.example.dragger2Test.subComponent.A1;
import com.example.dragger2Test.subComponent.B1;
import com.example.views.A1Activity;

import javax.inject.Inject;

public class A1PresenterImpl implements A1Constraint.A1Presenter {

    private static final String TAG = "A1PresenterImpl";

    @Inject
    public A1Activity a1View;

    @Inject
    public A1 a1;

    @Inject
    public B1 b1;

    @Inject
    public A1PresenterImpl(){
        Log.d(TAG, "A1PresenterImpl..........");
    }



}
