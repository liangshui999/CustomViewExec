package com.example.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 创建日期：2017-12-13 on 15:41
 * 作者：ls
 */

public class DisPatchTouchLinearLayout extends LinearLayout {

    public DisPatchTouchLinearLayout(Context context) {
        super(context);
    }

    public DisPatchTouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisPatchTouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                return false;

        }
        return super.onInterceptTouchEvent(ev);
    }
}
