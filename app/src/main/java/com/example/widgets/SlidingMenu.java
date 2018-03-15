package com.example.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义的侧滑菜单
 * 创建日期：2018-03-15 on 13:45
 * 作者：ls
 */

public class SlidingMenu extends ViewGroup {

    private static final String TAG = "SlidingMenu";

    private int mTouchSlop;//被判定为滑动的最小距离

    private Scroller mScroller;//弹性滑动

    private VelocityTracker mVelocityTracker;//速度跟踪器

    private float mFirstX;

    private float mFirstY;

    private float mLastX;

    private float mLastY;

    private int menuWidth;//菜单的宽度

    private int contentWidth;//内容的宽度

    private View menu;//菜单

    private View content;//内容

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.getTouchSlop();
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量所有的子view
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "l = " + l + "....." + "t = " + t + "....." + "r = " + r + "....." + "b = " + b);
        menu = getChildAt(0);
        content = getChildAt(1);
        menuWidth = menu.getMeasuredWidth();
        contentWidth = content.getMeasuredWidth();
        menu.layout(-1 * menuWidth, 0, 0, menu.getMeasuredHeight());
        content.layout(0, 0, contentWidth, content.getMeasuredHeight());
        Log.d(TAG, "content.getMeasuredWidth() = " + content.getMeasuredWidth());
        Log.d(TAG, "content.getMeasuredHeight() = " + content.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent.........ACTION_DOWN");
                mFirstX = mLastX = ev.getX();
                mFirstY = mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent.........ACTION_MOVE");
                float deltaX = ev.getX() - mLastX;
                float deltaY = ev.getY() - mLastY;
                if(Math.abs(deltaX) > mTouchSlop && Math.abs(deltaX) > Math.abs(deltaY)){
                    return true;
                }
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        mVelocityTracker.computeCurrentVelocity(1000);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent.........ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent.........ACTION_MOVE");
                Log.d(TAG, "getScrollX() = " + getScrollX());
                //边界判断和处理
                if (checkBound(event)) return true;
                //手指按在屏幕上滑动的处理
                float deltaX = event.getX() - mLastX;
                scrollBy((int) (-1 * deltaX), 0);
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_UP: //根据滚动的距离判断怎么还原
                Log.d(TAG, "onTouchEvent.........ACTION_UP");
                int vx = (int) Math.abs(mVelocityTracker.getXVelocity());
                Log.d(TAG, "vx = " + vx);
                //快速滑动的处理,未必一定是200
                if(vx > 200){
                    if(event.getX() - mFirstX > 0){ //向右快速滑动
                        showMenuComplete();
                        return true;
                    }else{ //向左快速滑动
                        hideMenuComplete();
                        return true;
                    }
                }
                //正常滑动的处理
                if(getScrollX() > -1 * menuWidth && getScrollX() <= -0.5f * menuWidth){ //拉出过半了
                    showMenuComplete();
                    return true;
                }
                if(getScrollX() > -0.5f * menuWidth && getScrollX() <= 0){ //拉出未过半
                    hideMenuComplete();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 完全隐藏menu
     */
    private void hideMenuComplete() {
        mScroller.startScroll(getScrollX(), 0, 0 - getScrollX(), 0);
        invalidate();
    }

    /**
     * 完全展示menu
     */
    private void showMenuComplete() {
        mScroller.startScroll(getScrollX(), 0, -1 * menuWidth - getScrollX(), 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 边界的判断和处理
     */
    private boolean checkBound(MotionEvent event) {
        float tempX = event.getX() - mFirstX;
        if(tempX > 0){ //说明手指在向右滑动
            if(getScrollX() <= -1 * menuWidth){
                scrollTo(-1 * menuWidth, 0);
                Log.d(TAG, "已经碰到边界了...不能再往右滑动");
                return true;
            }
        }else{ //说明手指在向左滑动
            if(getScrollX() >= 0){
                scrollTo(0, 0);
                Log.d(TAG, "已经碰到边界了...不能再往左滑动");
                return true;
            }
        }
        return false;
    }
}
