package com.example.aidl.binder_pool;

import android.os.RemoteException;

import com.example.aidl.IStringManager;

/**
 * 创建日期：2018-01-04 on 16:17
 * 作者：ls
 */

public class StringManager extends IStringManager.Stub {

    @Override
    public String getUpper(String s) throws RemoteException {
        return s.toUpperCase();
    }

}
