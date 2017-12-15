package com.example.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.callback.OnDelBtnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017-12-14 on 10:14
 * 作者：ls
 * 向右滑动删除item的listview
 * 最核心的一点是button必须设置宽度
 *  设置宽度是一个很核心的部分，不设置宽度是无效的
 <Button
 android:id="@+id/btn_delete"
 android:layout_width="150dp"
 android:layout_height="match_parent"
 android:text="删除"
 android:background="@color/colorPrimary"
 />
 */

public class SlideDeleteItemListView extends ListView {

    private static final String TAG = "DeleteItemListView";

    private float mFirstX;

    private float mLastX;

    private float mLastY;

    private float mTouchSlop;

    /**
     * 触摸点的索引
     */
    int mTouchedItemIndex = 0;

    /**
     * 触摸点的索引减去第一个可见item的索引
     * 比如触摸点的索引是23，第一个可见item的索引是18，
     * 则该值为5
     */
    int mChildPosition = 0;

    /**
     * item的布局
     */
    LinearLayout mLL = null;

    /**
     * 删除按钮的宽度
     */
    float mDelWidth = 0;

    /**
     * 点击了删除按钮的监听器
     */
    private OnDelBtnClickListener mOnDelBtnClickListener;

    /**
     * 每个item显示删除按钮与否的集合
     */
    private List<Boolean> mIsDelBtnShows;

    /**
     * 主要用于初始化mIsDelBtnShows
     */
    private boolean mOnce = true;

    /**
     * 是否快速滑动过
     */
    private boolean mIsFlilling = false;

    private Scroller mScroller;

    /**
     * 速度跟踪器
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 定时任务间隔的时间
     */
    private static final int TIME = 50;

    /**
     * 动画的时间
     */
    private static final int DURATION_TIME = 500;

    public SlideDeleteItemListView(Context context) {
        this(context, null);
    }

    public SlideDeleteItemListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDeleteItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.getTouchSlop();
        mIsDelBtnShows = new ArrayList<>();
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mOnce){
            for(int i = 0; i < this.getAdapter().getCount(); i++){
                mIsDelBtnShows.add(false);
            }
            mOnce = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchedItemIndex = pointToPosition((int)x, (int)y); //根据触摸点的xy坐标获取被触摸item的index
                if(mTouchedItemIndex < 0){ //说明没有触摸在item上，而是触摸在间隙上了
                    return super.onTouchEvent(ev);
                }
                mChildPosition = mTouchedItemIndex - getFirstVisiblePosition();
                mLL = (LinearLayout) getChildAt(mChildPosition);//注意这里采用的是mChildPosition
                mDelWidth = mLL.getChildAt(1).getWidth();
                mLL.getChildAt(1).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLL.scrollTo(0, 0);
                        mIsDelBtnShows.remove(mTouchedItemIndex);
                        mOnDelBtnClickListener.onClick(mTouchedItemIndex);
                    }
                });
                Log.d(TAG, "mTouchedItemIndex = " + mTouchedItemIndex + "......." + "getFirstVisiblePosition() = "
                        + getFirstVisiblePosition() + "........." + "mChildPosition = " + mChildPosition);
                mFirstX = x;
                mLastX = x;
                mLastY = y;
                mIsFlilling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mTouchedItemIndex < 0){ //说明没有触摸在item上，而是触摸在间隙上了
                    return super.onTouchEvent(ev);
                }

                float absDeltaX = Math.abs(x - mLastX);
                float absDeltaY = Math.abs(y - mLastY);
                if(! mIsDelBtnShows.get(mTouchedItemIndex)){ //删除按钮没有显示出来,只管向左的滑动
                    if(x - mFirstX < 0 && Math.abs(x - mFirstX) > mTouchSlop
                            && Math.abs(x - mFirstX) <= mDelWidth && absDeltaX > absDeltaY){
                        mVelocityTracker.clear();
                        mVelocityTracker.addMovement(ev);
                        mVelocityTracker.computeCurrentVelocity(100);
                        float xV = mVelocityTracker.getXVelocity();
                        Log.d(TAG, "xV = " + xV);
                        if(Math.abs(xV) > 100){
                            mIsFlilling = true;
                        }
                        float deltaX = x - mLastX;
                        mLL.scrollBy((int)(-1.0f * deltaX), 0);
                    }
                }else{ //删除按钮显示出来,只管向右的滑动
                    if(x - mFirstX > 0 && Math.abs(x - mFirstX) > mTouchSlop
                            && Math.abs(x - mFirstX) <= mDelWidth  && absDeltaX > absDeltaY){
                        float deltaX = x - mLastX;
                        mLL.scrollBy((int)(-1.0f * deltaX), 0);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(mTouchedItemIndex < 0){ //说明没有触摸在item上，而是触摸在间隙上了
                    return super.onTouchEvent(ev);
                }
                float currentScrollX = mLL.getScrollX();
                if(! mIsDelBtnShows.get(mTouchedItemIndex)){ //删除按钮没有显示出来
                    if(mIsFlilling){ //快速滑动过
                        if(currentScrollX > 0){
                            smoothScroll(currentScrollX, mDelWidth - currentScrollX);
                            mIsDelBtnShows.set(mTouchedItemIndex, true);
                        }
                    }else {
                        if(currentScrollX > 0 && currentScrollX >= mDelWidth / 2
                                && currentScrollX <= mDelWidth){ //向左滑动,且距离大于一半
                            smoothScroll(currentScrollX, mDelWidth - currentScrollX);
                            mIsDelBtnShows.set(mTouchedItemIndex, true);
                        }else if(currentScrollX > 0 && currentScrollX < mDelWidth / 2
                                && currentScrollX > mTouchSlop){ //向左滑动，但距离小于一半
                            smoothScroll(currentScrollX, -1.0f * currentScrollX);
                            mIsDelBtnShows.set(mTouchedItemIndex, false);
                        }else if(Math.abs(x - mFirstX) <= mTouchSlop){ //点击事件
                            if(getOnItemClickListener() != null){
                                getOnItemClickListener().onItemClick(this, mLL, mTouchedItemIndex, mTouchedItemIndex);
                            }
                        }
                    }

                }else{ //删除按钮显示出来了
                    if(x - mFirstX > 0 && Math.abs(x - mFirstX) > mTouchSlop
                            && Math.abs(x - mFirstX) <= mDelWidth){ //向右滑动
                        smoothScroll(currentScrollX, -1.0f * currentScrollX);
                        mIsDelBtnShows.set(mTouchedItemIndex, false);
                    }else if(Math.abs(x - mFirstX) <= mTouchSlop){ //点击事件
                        if(getOnItemClickListener() != null){
                            getOnItemClickListener().onItemClick(this, mLL, mTouchedItemIndex, mTouchedItemIndex);
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void setmOnDelBtnClickListener(OnDelBtnClickListener mOnDelBtnClickListener) {
        this.mOnDelBtnClickListener = mOnDelBtnClickListener;
    }


    /**
     * 缓慢滚动
     * @param startX 起始的scrollX
     * @param dx 差值
     */
    private void smoothScroll(float startX, float dx){
        mScroller.startScroll((int)startX, 0, (int) dx, 0, DURATION_TIME);
        post(new ScrollRunnable());
    }


    /**
     * 定时滚动的任务
     */
    class ScrollRunnable implements Runnable{

        @Override
        public void run() {
            Log.d(TAG, "mScroller.computeScrollOffset() = " + mScroller.computeScrollOffset());
            if(mScroller.computeScrollOffset()){ //说明还没有结束
                mLL.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                postDelayed(this, TIME);
                Log.d(TAG, "mScroller.getCurrX() = " + mScroller.getCurrX());
            }else{
                mLL.scrollTo(mScroller.getFinalX(), 0);
            }
        }
    }



    /*if(! mIsDelBtnShows.get(mTouchedItemIndex)){ //删除按钮没有显示出来
        if(x - mFirstX < 0 && Math.abs(x - mFirstX) >= mDelWidth / 2
                && Math.abs(x - mFirstX) <= mDelWidth){ //向左滑动,且距离大于一半
            smoothScroll(Math.abs(x - mFirstX), mDelWidth - Math.abs(x - mFirstX));
            mIsDelBtnShows.set(mTouchedItemIndex, true);
        }else if(x - mFirstX < 0 && Math.abs(x - mFirstX) < mDelWidth / 2
                && Math.abs(x - mFirstX) > mTouchSlop){ //向左滑动，但距离小于一半
            smoothScroll(Math.abs(x - mFirstX), x - mFirstX);
            mIsDelBtnShows.set(mTouchedItemIndex, false);
        }else if(Math.abs(x - mFirstX) <= mTouchSlop){ //点击事件
            if(getOnItemClickListener() != null){
                getOnItemClickListener().onItemClick(this, mLL, mTouchedItemIndex, mTouchedItemIndex);
            }
        }
    }else{ //删除按钮显示出来了
        if(x - mFirstX > 0 && Math.abs(x - mFirstX) > mTouchSlop
                && Math.abs(x - mFirstX) <= mDelWidth){ //向右滑动
            smoothScroll(mDelWidth - Math.abs(x - mFirstX), -1.0f * (mDelWidth - Math.abs(x - mFirstX)));
            mIsDelBtnShows.set(mTouchedItemIndex, false);
        }else if(Math.abs(x - mFirstX) <= mTouchSlop){ //点击事件
            if(getOnItemClickListener() != null){
                getOnItemClickListener().onItemClick(this, mLL, mTouchedItemIndex, mTouchedItemIndex);
            }
        }
    }*/


}
