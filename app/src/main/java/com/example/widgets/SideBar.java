package com.example.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;


/**
 * 创建日期：2018-03-14 on 15:43
 * 作者：ls
 */

public class SideBar extends View {

    private static final String TAG = "SideBar";

    private static char[] initials = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};

    private Paint mPaint;

    private ListView mListView;

    private MyDialog dialog;

    private int itemHeight;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(18);
        mPaint.setColor(Color.GREEN);
        dialog = new MyDialog(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        itemHeight = height / initials.length;
        Log.d(TAG, "height = " + height + "........" + "itemHeight = " + itemHeight);
        for(int i = 0; i < initials.length; i++){
            canvas.drawText(String.valueOf(initials[i]), width / 2, (i + 1) * itemHeight, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){
            int y = (int) event.getY();
            int index = y / itemHeight; //注意这个地方的除数
            Log.d(TAG, "y = " + y + "...." + "index = " + index);
            char content = initials[index];
            SectionIndexer sectionIndexer = (SectionIndexer) mListView.getAdapter();
            int position = sectionIndexer.getPositionForSection(content);
            mListView.setSelection(position);
            dialog.setMsg("" + content);
            dialog.show();
        }else if(action == MotionEvent.ACTION_UP){
            dialog.dismiss();
        }

        return true;
    }

    public void setmListView(ListView mListView) {
        this.mListView = mListView;
    }
}
