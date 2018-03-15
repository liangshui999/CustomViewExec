package com.example.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.activity.R;

/**
 * 创建日期：2017-12-06 on 11:18
 * 作者：ls
 * 主要是用于测试matrix前置和后置的区别
 */

public class MatrixtTestView extends ImageView {

    private Matrix mMatrix;

    private Bitmap mBitmap;

    private Paint mPaint;

    public MatrixtTestView(Context context) {
        this(context, null);
    }

    public MatrixtTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixtTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mMatrix.postRotate(30);
        mMatrix.postTranslate(500, 500);
        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }
}
