package com.example.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.constraint.A1Constraint;
import com.example.presenters.A1PresenterImpl;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class A1Activity extends Activity implements A1Constraint.A1View {

    private static final String TAG = "A1Activity";

    @Inject
    A1PresenterImpl a1Presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "a1Presenter = " + a1Presenter);
        Log.d(TAG, "注入给presenter的activity = " + a1Presenter.a1View);
        Log.d(TAG, "注入给presenter的a1 = " + a1Presenter.a1);
        Log.d(TAG, "注入给presenter的b1 = " + a1Presenter.b1);
    }

}
