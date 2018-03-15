package com.example.aidl.binder_pool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 创建日期：2018-01-04 on 16:23
 * 作者：ls
 */

public class BinderPoolService extends Service {

    private static final String TAG = "BinderPoolService";

    private BinderManager mBinderManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinderManager = new BinderManager();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "mBinderManager = " + mBinderManager);
        return mBinderManager;
    }
}
