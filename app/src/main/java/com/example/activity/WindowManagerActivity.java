package com.example.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 创建日期：2018-01-02 on 9:26
 * 作者：ls
 */

public class WindowManagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);
        WindowManager windowManager = getWindowManager();
        Button btn = new Button(this);
        btn.setBackgroundResource(R.color.colorPrimary);
        btn.setText("按钮");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(300,
                300, 0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.alpha = 0.3f;
        windowManager.addView(btn, layoutParams);





    }
}
