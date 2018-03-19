package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.callback.OnRefreshOrLoadListener;
import com.example.widgets.swipeLoad.SwipeLoadLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018-03-16 on 14:23
 * 作者：ls
 */

public class SwipeloadActivity extends Activity {

    private static final String TAG = "SwipeloadActivity";
    private SwipeLoadLayout mSwipeLoadLayout;
    private ListView mListView;

    private MyHandler handler = new MyHandler();

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "msg == " + msg);
            switch (msg.what){
                case 1:
                    mSwipeLoadLayout.refreshComplete();
                    break;
                case 2:
                    mSwipeLoadLayout.loadComplete();
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeload);
        mSwipeLoadLayout = (SwipeLoadLayout) findViewById(R.id.swipe_layout);
        mSwipeLoadLayout.setmOnRefreshOrLoadListener(new OnRefreshOrLoadListener() {
            @Override
            public void onRefresh(View v) {
                Log.d(TAG, "正在刷新......." + v);
                handler.sendEmptyMessageDelayed(1, 2000);
                Log.d(TAG, "刷新完毕......." );
            }

            @Override
            public void onLoad(View v) {
                Log.d(TAG, "正在加载......." + v);
                handler.sendEmptyMessageDelayed(2, 2000);
                Log.d(TAG, "加载完毕......." );
            }
        });
        mListView = (ListView) findViewById(R.id.list_swipe);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add("i = " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);


    }

    /**
     * 睡眠
     */
    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
