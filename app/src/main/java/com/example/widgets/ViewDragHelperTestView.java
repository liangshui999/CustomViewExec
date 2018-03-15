package com.example.widgets;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 创建日期：2017-12-11 on 17:10
 * 作者：ls
 */

public class ViewDragHelperTestView extends LinearLayout {

    private static final String TAG = "ViewDragHelperTestView";

    private ImageView mImgOne;

    private ViewDragHelper mViewDragHelper;

    public ViewDragHelperTestView(Context context) {
        this(context, null);
    }

    public ViewDragHelperTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //mImgOne = (ImageView) findViewById(R.id.img_one);
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback(){

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.d(TAG, "tryCaptureView......");
                if(mImgOne == child){
                    return true;
                }
                return false;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                //Log.d(TAG, "onViewPositionChanged.....");
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.d(TAG, "onViewReleased........");
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                Log.d(TAG, "onEdgeTouched.......");
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                Log.d(TAG, "onEdgeDragStarted.......");
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }


            @Override
            public int getViewHorizontalDragRange(View child) {
                Log.d(TAG, "getViewHorizontalDragRange.......");
                super.getViewHorizontalDragRange(child);
                return 1;
            }


            @Override
            public int getViewVerticalDragRange(View child) {
                Log.d(TAG, "getViewVerticalDragRange.......");
                super.getViewVerticalDragRange(child);
                return 1;
            }

            /**
             * 横向上view应该放在的位置
             * @param child
             * @param left
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d(TAG, "clampViewPositionHorizontal.......");
                return left;
            }

            /**
             * 纵向上view应该放在的位置
             * @param child
             * @param top
             * @param dy
             * @return
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.d(TAG, "clampViewPositionVertical.......");
                return top;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
