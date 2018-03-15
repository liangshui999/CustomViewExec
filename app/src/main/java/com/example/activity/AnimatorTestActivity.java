package com.example.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 创建日期：2018-03-08 on 15:50
 * 作者：ls
 */

public class AnimatorTestActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "AnimatorTestActivity";

    private Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_test);
        mBtnTest = (Button) findViewById(R.id.btn_first);
        mBtnTest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_first:
                float currentTranX = mBtnTest.getTranslationX();
                Log.d(TAG, "currentTranX = " + currentTranX);
                float temp = currentTranX + 100;
                if(temp >= 500){
                    temp = 0;
                }
                ObjectAnimator.ofFloat(mBtnTest, "translationX", currentTranX, temp).setDuration(3000).start();
                break;
        }
    }
}
