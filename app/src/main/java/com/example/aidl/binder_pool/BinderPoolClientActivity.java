package com.example.aidl.binder_pool;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.IBinderManager;
import com.example.aidl.ICalculateManager;
import com.example.aidl.IStringManager;

/**
 * 创建日期：2018-01-04 on 16:26
 * 作者：ls
 */

public class BinderPoolClientActivity extends Activity {

    private static final String TAG = "BinderPoolClientActivit";

    private ServiceConnection mServiceConnection;

    private IBinderManager mBinderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(TAG, "service = " + service);
                mBinderManager = BinderManager.Stub.asInterface(service);
                Log.e(TAG, "mBinderManager = " + mBinderManager);
                Log.e(TAG, "****************************查找CalculateManager**********************************");
                try {
                    //查找CalculateManager
                    IBinder iBinder = mBinderManager.query(BinderConstant.CODE_CACAULATE);
                    Log.e(TAG, "iBinder = " + iBinder);
                    ICalculateManager cm = CalculateManager.Stub.asInterface(iBinder);//注意这里一定要用接口接，否则类型转换错误
                    Log.e(TAG, "cm = " + cm);
                    Log.e(TAG, "cm.add(3, 5) = " + cm.add(3, 5));
                    Log.e(TAG, "*****************************查找StringManager*********************************");

                    //查找StringManager
                    iBinder = mBinderManager.query(BinderConstant.CODE_STRING);
                    Log.e(TAG, "iBinder = " + iBinder);
                    IStringManager sm =  StringManager.Stub.asInterface(iBinder);//注意这里一定要用接口接，否则类型转换错误
                    Log.e(TAG, "sm = " + sm);
                    Log.e(TAG, "sm.getUpper = " + sm.getUpper("abcdef"));

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, BinderPoolService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
