package com.example.widgets;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 创建日期：2017-12-06 on 14:25
 * 作者：ls
 * 可缩放的imageview
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private static final String TAG = "ZoomImageView";

    /**
     * 该view的宽
     */
    private int mWidth;

    /**
     * 该view的高
     */
    private int mHeight;

    /**
     * 缩放的矩阵
     */
    private Matrix mZoomMatrix;

    /**
     * 记录缩放矩阵信息的数组
     */
    private float[] mMatrixValuesArr;

    /**
     * 主要用于onGlobalLayout（）方法中
     * 该方法可能会多次回调，我们只希望他调用一次
     */
    private boolean mIsFirst = true;

    /**
     * 缩放手势的监听器
     */
    private ScaleGestureDetector mScaleGestureDetector;

    /**
     * 上一次触摸点的x坐标
     */
    private float mLastX;

    /**
     * 上一次触摸点的y坐标
     */
    private float mLastY;

    /**
     * 是否缩放过.主要是为了防止缩放后，离开屏幕时，手指还有可能在屏幕上触摸，造成的跳跃
     */
    private boolean mHasScale = false;

    /**
     * 原始图片所在的区域
     */
    private RectF mOrignalRectF;

    /**
     * 变化后的图片所在的区域
     */
    private RectF mConvertedRectF;

    /**
     * 最大容许放大的倍数,相对于最原始的图
     */
    private static float SCALE_MAX = 4.0f;

    /**
     * 最小容许的压缩倍数,相对于最原始的图
     */
    private static float SCALE_MIN = 0.1f;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化的方法
     */
    private void init() {
        //注意这里一定要设置缩放的模式，否则是matrix是无效的
        super.setScaleType(ScaleType.MATRIX);
        mZoomMatrix = new Matrix();
        mMatrixValuesArr = new float[9];
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        this.setOnTouchListener(this);
        mOrignalRectF = new RectF();
        mConvertedRectF = new RectF();
        Log.d(TAG, "mWidth = " + mWidth + "......." + "mHeight = " + mHeight);
    }



    /************************************************************************************/


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        Log.d(TAG, "onGlobalLayout............");
        if(! mIsFirst){
            return;
        }
        mWidth = getWidth();
        mHeight = getHeight();
        Drawable drawable = getDrawable();
        if(drawable == null){
            Log.d(TAG, "drawable为空");
            return;
        }
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();
        Log.d(TAG, "dWidth = " + dWidth + ".........." + "dHeight = " + dHeight);

        //设置原始图片的rectf
        mOrignalRectF.top = 0f;
        mOrignalRectF.right = dWidth;
        mOrignalRectF.bottom = dHeight;
        mOrignalRectF.left = 0f;

        float scale = 1.0f;
        float translateX = 0f;
        float translateY = 0f;
        if(dWidth > mWidth && dHeight <= mHeight){ //宽度大于屏幕，高度小于等于屏幕
            scale = 1.0f * dWidth / mWidth;
            translateX = -1.0f * (dWidth - mWidth) / 2;
            translateY = 1.0f * (mHeight - dHeight) / 2;
        }else if(dHeight > mHeight && dWidth <= mWidth){ //高度大于屏幕，宽度小于等于屏幕
            scale = 1.0f * dHeight / mHeight;
            translateX = 1.0f * (mWidth - dWidth) / 2;
            translateY = -1.0f * (dHeight - mHeight) / 2;
        }else if(dWidth > mWidth && dHeight > mHeight){ //宽度和高度都大于屏幕
            float wScale = 1.0f * dWidth / mWidth;
            float hScale = 1.0f * dHeight / mHeight;
            scale = wScale > hScale ? wScale : hScale;
            translateX = -1.0f * (dWidth - mWidth) / 2;
            translateY = -1.0f * (dHeight - mHeight) / 2;
        }else { //宽度和高度都小于屏幕
            scale = 1.0f;
            translateX = 1.0f * (mWidth - dWidth) / 2;
            translateY = 1.0f * (mHeight - dHeight) / 2;
        }
        Log.d(TAG, "scale = " + scale + "......" + "translateX = " + translateX + "....." + "translateY = " + translateY);

        scale = 1.0f / scale;
        mZoomMatrix.postTranslate(translateX, translateY);
        mZoomMatrix.postScale(scale, scale, mWidth / 2.0f, mHeight / 2.0f);//注意缩放中心点的设置
        setImageMatrix(mZoomMatrix);

        mIsFirst = false;

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent.....");
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //这里必须设置mLastX和mLastY，否则最开始移动的时候会图片会跳
                mLastX = x;
                mLastY = y;
                mHasScale = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                if(! mHasScale){
                    Log.d(TAG, "ACTION_MOVE.......");
                    float deltaX = x - mLastX;
                    float deltaY = y - mLastY;
                    //检验deltaX，deltaY是否超标
                    boolean isAllowed = checkeAllowedTranslate(deltaX, deltaY);
                    if(isAllowed){
                        //平移操作
                        mZoomMatrix.postTranslate(deltaX, deltaY);
                        setImageMatrix(mZoomMatrix);
                    }
                    mLastX = x;
                    mLastY = y;
                }
                break;

        }
        return true;
    }

    /**
     * 检验是否容许平移
     * @param deltaX 将要平移的x距离
     * @param deltaY 将要平移的y距离
     */
    private boolean checkeAllowedTranslate(float deltaX, float deltaY) {
        mZoomMatrix.mapRect(mConvertedRectF, mOrignalRectF);
        Log.d(TAG, "mOrignalRectF = " + mOrignalRectF + "......." + "mConvertedRectF = " + mConvertedRectF);
        if(mConvertedRectF.left <= 0 && mConvertedRectF.right >= mWidth
                && mConvertedRectF.top >= 0 && mConvertedRectF.bottom <= mHeight){ //左右超出，上下未超出
            if(mConvertedRectF.left + deltaX <= 0 && mConvertedRectF.right + deltaX >= mWidth
                    && mConvertedRectF.top + deltaY >= 0 && mConvertedRectF.bottom + deltaY <= mHeight){
                return true;
            }else{
                return false;
            }
        }else if(mConvertedRectF.left >= 0 && mConvertedRectF.right <= mWidth
                && mConvertedRectF.top <= 0 && mConvertedRectF.bottom >= mHeight){ //左右未超出，上下超出
            if(mConvertedRectF.left + deltaX >= 0 && mConvertedRectF.right + deltaX <= mWidth
                    && mConvertedRectF.top + deltaY <= 0 && mConvertedRectF.bottom + deltaY >= mHeight){
                return true;
            }else{
                return false;
            }
        }else if(mConvertedRectF.left >= 0 && mConvertedRectF.right <= mWidth
                && mConvertedRectF.top >= 0 && mConvertedRectF.bottom <= mHeight){ //左右未超出，上下未超出
            if(mConvertedRectF.left + deltaX >= 0 && mConvertedRectF.right + deltaX <= mWidth
                    && mConvertedRectF.top + deltaY >= 0 && mConvertedRectF.bottom + deltaY <= mHeight){
                return true;
            }else{
                return false;
            }
        }else if(mConvertedRectF.left <= 0 && mConvertedRectF.right >= mWidth
                && mConvertedRectF.top <= 0 && mConvertedRectF.bottom >= mHeight){ //左右超出，上下超出
            if(mConvertedRectF.left + deltaX <= 0 && mConvertedRectF.right + deltaX >= mWidth
                    && mConvertedRectF.top + deltaY <= 0 && mConvertedRectF.bottom + deltaY >= mHeight){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }


    /******************************************和缩放的监听相关******************************************/

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d(TAG, "onScale.......");
        scale(detector);
        //这儿必须返回true,返回false效果非常诡异
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleBegin......");
        mHasScale = true;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleEnd.........");
    }

    /******************************************和缩放的监听相关******************************************/


    private void scale(ScaleGestureDetector detector){
        float scaleFactor = detector.getScaleFactor();//相对于上一次的缩放比例
        float focusX = detector.getFocusX();
        float focusY = detector.getFocusY();
        Log.d(TAG, "scale------" + "focusX = " + focusX + "......" + "focusY = " + focusY);

        float currentScale = getCurrentScale();
        Log.d(TAG, "currentScale = " + currentScale);
        //注意为什么是scaleFactor * currentScale,乘以代表是将来的
        if(scaleFactor * currentScale < SCALE_MIN){
            scaleFactor = SCALE_MIN / currentScale;
        }
        if(scaleFactor * currentScale > SCALE_MAX){
            scaleFactor = SCALE_MAX / currentScale;
        }
        Log.d(TAG, "scaleFactor = " + scaleFactor);
        mZoomMatrix.postScale(scaleFactor, scaleFactor,focusX, focusY);
        setImageMatrix(mZoomMatrix);
    }


    /**
     * 获取当前的缩放比例
     */
    private float getCurrentScale(){
        mZoomMatrix.getValues(mMatrixValuesArr);
        return mMatrixValuesArr[Matrix.MSCALE_X];
    }

}
