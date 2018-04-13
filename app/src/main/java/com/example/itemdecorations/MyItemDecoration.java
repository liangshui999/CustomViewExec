package com.example.itemdecorations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 创建日期：2018-03-21 on 16:20
 * 作者：ls
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "MyItemDecoration";

    private Paint mPaint;

    public static int HEIGHT = 40;

    public MyItemDecoration(){
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 在时间上先于item画，因此可能会被item覆盖
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawDecoration(c, parent);
    }

    /**
     * 在时间上后于item画，因此可能会覆盖item
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 不复写这个，只有onDraw（）的时候，分割线会被item所覆盖
     * 这个设置的是留一些可以用于onDraw的空白地方
     * 类似于margin的用法
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, HEIGHT, 0, HEIGHT);
    }

    private void drawDecoration(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int itemCount = parent.getAdapter().getItemCount();
        Log.d(TAG, "childCount = " + childCount + "........" + "itemCount = " + itemCount);
        for(int i = 0; i < childCount; i++){
            View itemView = parent.getChildAt(i);
            c.drawRect(0, itemView.getBottom(), itemView.getWidth(), itemView.getBottom() + HEIGHT, mPaint);
        }
    }

}
