package com.example.aidl.binder_pool;

import android.os.RemoteException;

import com.example.aidl.ICalculateManager;

/**
 * 创建日期：2018-01-04 on 16:16
 * 作者：ls
 */

public class CalculateManager extends ICalculateManager.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
