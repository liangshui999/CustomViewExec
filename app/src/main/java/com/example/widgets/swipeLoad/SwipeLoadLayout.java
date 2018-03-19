package com.example.widgets.swipeLoad;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.callback.OnRefreshOrLoadListener;

/**
 * 创建日期：2018-03-16 on 10:33
 * 作者：ls
 * 自定义的上拉加载和下拉刷新控件，针对所有的view
 */

public class SwipeLoadLayout extends ViewGroup {

    private static final String TAG = "SwipeLoadLayout";

    private float mTouchSlop;//被认为是滑动的最小距离

    private VelocityTracker mVelocityTracker;//速度跟踪器

    private Scroller mScroller;

    private State mState = State.DEFAULT;//下拉刷新，上拉加载过程中的各种状态

    private OnRefreshOrLoadListener mOnRefreshOrLoadListener;

    private MyOnScrollListener mOnScrollListener;

    private ViewGroup mRefreshLayout;

    private View mContentLayout;

    private ViewGroup mLoadLayout;

    private TextView mRefreshText;

    private TextView mLoadText;

    private float mFirstX;

    private float mFirstY;

    private float mLastX;

    private float mLastY;

    private static final String TAG_REFRESH = "tag_refresh";

    private static final String TAG_LOAD = "tag_load";

    private static final int V_LIMIT = 100;//速度限制

    public SwipeLoadLayout(Context context) {
        this(context, null);
    }

    public SwipeLoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.getTouchSlop();
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mOnScrollListener = new MyOnScrollListener();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mVelocityTracker != null){
            mVelocityTracker.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure.........");
        for(int i = 0; i < getChildCount(); i++){
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout...........");
        if(mContentLayout == null){
            mRefreshLayout = (ViewGroup) getChildAt(0);
            mContentLayout = getChildAt(1);
            mLoadLayout = (ViewGroup) getChildAt(2);
            mRefreshText = (TextView) mRefreshLayout.findViewWithTag(TAG_REFRESH);
            mLoadText = (TextView) mLoadLayout.findViewWithTag(TAG_LOAD);
            if(mContentLayout instanceof AbsListView){
                AbsListView temp = (AbsListView) mContentLayout;
                temp.setOnScrollListener(mOnScrollListener);
            }
        }
        mRefreshLayout.layout(0, -1 * mRefreshLayout.getMeasuredHeight(), r, 0);
        mContentLayout.layout(0, 0, r, b);
        mLoadLayout.layout(0, b, r, b + mLoadLayout.getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //计算速度
        mVelocityTracker.addMovement(ev);
        mVelocityTracker.computeCurrentVelocity(100);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent.......");
        float distanceX = ev.getX() - mFirstX;
        float distanceY = ev.getY() - mFirstY;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent..........MotionEvent.ACTION_DOWN");
                mFirstX = mLastX = ev.getX();
                mFirstY = mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent..........MotionEvent.ACTION_MOVE");
                if(Math.abs(distanceY) > mTouchSlop && Math.abs(distanceY) > Math.abs(distanceX)){
                    if(distanceY > 0){ //手指向下滑动
                        if(! canChildScrollDown(mContentLayout)){ //child不能向下滑动，才拦截
                            return true;
                        }
                    }else{ //手指向上滑动
                        if(! canChildScrollUp(mContentLayout)){ //child不能向上滑动，才拦截
                            return true;
                        }
                    }
                }
                break;

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent.......");
        float scrollY = getScrollY();
        float distanceY = event.getY() - mFirstY;
        float deltaY = event.getY() - mLastY;
        Log.d(TAG, "distanceY = " + distanceY + "......." + "mRefreshLayout.getHeight() = "
                + mRefreshLayout.getHeight() + "......." + "mLoadLayout.getHeight() = "
                + mLoadLayout.getHeight());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent.........ACTION_DOWN");
                mRefreshText.setText("");
                mLoadText.setText("");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent.........ACTION_MOVE");
                //检查是否是正在刷新或者是正在加载状态
                if(mState == State.REFRESHING || mState == State.LOADING){
                    Log.d(TAG, "当前正在刷新或者是正在加载");
                    return true;
                }

                //检查边界
                if(distanceY > 0 && scrollY <= -1 * mRefreshLayout.getHeight()){
                    Log.d(TAG, "不能再向下滑动了.....");
                    scrollTo(0, -1 * mRefreshLayout.getHeight());
                    return true;
                }
                if(distanceY < 0 && scrollY >= mLoadLayout.getHeight()){
                    Log.d(TAG, "不能再向上滑动了.....");
                    scrollTo(0, mLoadLayout.getHeight());
                    return true;
                }

                //处理手指滑动的逻辑
                if(distanceY > 0){ //手指向下滑动
                    if(distanceY >= mRefreshLayout.getHeight() / 2){ //释放刷新
                        mRefreshText.setText("释放刷新");
                        mState = State.RELEASE_TO_REFRESH;
                    }else{
                        mRefreshText.setText("下拉刷新");
                        mState = State.PULL_TO_REFRESH;
                    }
                }else{ //手指向上滑动
                    if(Math.abs(distanceY) >= mLoadLayout.getHeight() / 2){ //释放加载
                        mLoadText.setText("释放加载");
                        mState = State.RELEASE_TO_LOAD;
                    }else{
                        mLoadText.setText("上拉加载");
                        mState = State.PULL_TO_LOAD;
                    }
                }
                scrollBy(0, (int) (-1f * deltaY));
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(mState == State.PULL_TO_REFRESH){ //下拉刷新
                    mScroller.startScroll(0, getScrollY(), 0, 0 - getScrollY());
                    mState = State.DEFAULT;
                }else if(mState == State.RELEASE_TO_REFRESH){ //释放刷新
                    mScroller.startScroll(0, getScrollY(), 0, -1 * mRefreshLayout.getHeight() - getScrollY());
                    mState = State.REFRESHING;
                    mRefreshText.setText("正在刷新....");
                    mOnRefreshOrLoadListener.onRefresh(mContentLayout);//正在刷新的回调
                }else if(mState == State.PULL_TO_LOAD){ //上拉加载
                    mScroller.startScroll(0, getScrollY(), 0, 0 - getScrollY());
                    mState = State.DEFAULT;
                }else if(mState == State.RELEASE_TO_LOAD){ //释放加载
                    releaseToLoad();
                }
                //重绘
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    /**
     * 释放加载
     */
    private void releaseToLoad() {
        mScroller.startScroll(0, getScrollY(), 0, mLoadLayout.getHeight() - getScrollY());
        mState = State.LOADING;
        mLoadText.setText("正在加载....");
        mOnRefreshOrLoadListener.onLoad(mContentLayout);//正在加载的回调
    }


    /**
     * 刷新完毕
     */
    public void refreshComplete(){
        mScroller.startScroll(0, getScrollY(), 0, 0 - getScrollY());
        mState = State.DEFAULT;
        invalidate();//务必记得要重绘
    }

    /**
     * 加载完毕
     */
    public void loadComplete(){
        mScroller.startScroll(0, getScrollY(), 0, 0 - getScrollY());
        mState = State.DEFAULT;
        invalidate();//务必记得要重绘
    }


    /**
     * 判断子view能否向下滚动
     * @return true代表能向下滚动，false代表不能向下滚动
     */
    private boolean canChildScrollDown(View view){
        if(Build.VERSION.SDK_INT < 14){
            Log.d(TAG, "Build.VERSION.SDK_INT > 14...........canChildScrollDown");
            if(view instanceof AbsListView){
                AbsListView temp = (AbsListView) view;
                if(temp.getFirstVisiblePosition() == 0
                        && temp.getChildAt(0).getTop() >= temp.getPaddingTop()){
                    return false;
                }else{
                    return true;
                }
            }else{
                if(view.getScrollY() <= 0){
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            Log.d(TAG, "view.canScrollVertically(-1) = " + view.canScrollVertically(-1));
            return view.canScrollVertically(-1);
        }
    }


    /**
     * 判断view能否向上滚动
     * @return true代表能向上滚动，false代表不能向上滚动
     */
    public boolean canChildScrollUp(View view){
        if(Build.VERSION.SDK_INT < 14){
            Log.d(TAG, "Build.VERSION.SDK_INT > 14...........canChildScrollUp");
            if(view instanceof AbsListView){
                AbsListView temp = (AbsListView) view;
                int count = temp.getCount();
                View lastItem = getLastItemView(temp);
                Log.d(TAG, "lastItem = " + lastItem);
                if(temp.getLastVisiblePosition() == count - 1
                        && lastItem.getBottom() <= temp.getHeight() - temp.getPaddingBottom()){
                    return false;
                }else{
                    return true;
                }
            }else{
                if(view.getScrollY() >= 0){
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            Log.d(TAG, "view.canScrollVertically(1) = " + view.canScrollVertically(1));
            return view.canScrollVertically(1);
        }
    }

    public void setmOnRefreshOrLoadListener(OnRefreshOrLoadListener mOnRefreshOrLoadListener) {
        this.mOnRefreshOrLoadListener = mOnRefreshOrLoadListener;
    }

    /**
     * 监听listview或者是gridview的滚动
     */
    class MyOnScrollListener implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int childCount = view.getChildCount();
            if(childCount == 0){
                return;
            }
            View lastItem = getLastItemView(view);
            if(view.getLastVisiblePosition() == totalItemCount - 1
                    && lastItem.getBottom() <= view.getHeight() - view.getPaddingBottom()){
                if(Math.abs(mVelocityTracker.getYVelocity()) > V_LIMIT){
                    Log.d(TAG, "onScroll............");
                    releaseToLoad();
                }

            }
        }
    }

    /**
     * 获取AbsListView这个的最后一个item
     * @param absListView
     */
    private View getLastItemView(AbsListView absListView) {
        int lastIndex = 0;
        int childCount = absListView.getChildCount();
        int itemCount = absListView.getCount();
        if(itemCount <=  childCount){
            lastIndex = itemCount - 1;
        }else{
            lastIndex = childCount - 1;
        }
        Log.d(TAG, "childCount = " + childCount + "......." + "itemCount = " + itemCount);
        return absListView.getChildAt(lastIndex);
    }
}
