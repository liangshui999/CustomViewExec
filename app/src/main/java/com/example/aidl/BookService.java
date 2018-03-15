package com.example.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 创建日期：2018-01-04 on 9:15
 * 作者：ls
 */

public class BookService extends Service {

    private static final String TAG = "BookService";

    private BookManager mBookManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mBookManager = new BookManager();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind.......");
        Log.e(TAG, "mBookManager = " + mBookManager);
        return mBookManager;
    }
}
