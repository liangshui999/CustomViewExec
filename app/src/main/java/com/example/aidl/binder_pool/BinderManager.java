package com.example.aidl.binder_pool;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.IBinderManager;

/**
 * 创建日期：2018-01-04 on 16:18
 * 作者：ls
 * 这个类类似于一个dns服务器
 */

public class BinderManager extends IBinderManager.Stub {

    private static final String TAG = "BinderManager";

    @Override
    public IBinder query(int code) throws RemoteException {
        switch (code){
            case BinderConstant.CODE_CACAULATE:
                CalculateManager calculateManager = new CalculateManager();
                Log.e(TAG, "calculateManager = " + calculateManager);
                return calculateManager;
            case BinderConstant.CODE_STRING:
                StringManager stringManager = new StringManager();
                Log.e(TAG, "stringManager = " + stringManager);
                return stringManager;
        }
        return null;
    }
}
