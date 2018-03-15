package com.example.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 * 创建日期：2018-01-04 on 9:26
 * 作者：ls
 */

public class ClientActivity extends Activity {

    private static final String TAG = "ClientActivity";

    private ServiceConnection mServiceConnection;

    private IOnNewBookArrivedListener.Stub mListener;

    private IBookManager mBookManager;

    private IBinder.DeathRecipient mDeathRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, BookService.class);
        mListener = new IOnNewBookArrivedListener.Stub() {
            @Override
            public void onNewBookArrive(Book book) throws RemoteException {
                Log.e(TAG, "回调回来的新书是 book = " + book);
            }
        };
        Log.e(TAG, "listener = " + mListener);

        //binder死亡的时候的通知
        mDeathRecipient = new IBinder.DeathRecipient() {
            @Override
            public void binderDied() {
                Log.e(TAG, "binderDied.......currentThread = " + Thread.currentThread());
            }
        };

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    service.linkToDeath(mDeathRecipient, 0); //设置死亡代理,注意使用的是IBinder对象
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "service = " + service);
                mBookManager = IBookManager.Stub.asInterface(service);
                Log.e(TAG, "mBookManager = " + mBookManager);

                try {
                    mBookManager.registerBookArriveListener(mListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                try {
                    List<Book> books = mBookManager.getBookList();
                    Log.e(TAG, "最初的books = " + books);
                    mBookManager.addBook(new Book(3, "新添加的一本书"));
                    books = mBookManager.getBookList();
                    Log.e(TAG, "添加之后再次获取的books = " + books);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(TAG, "onServiceDisconnected.......currentThread = " + Thread.currentThread());
            }
        };
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        try {
            mBookManager.unRegisterBookArriveListener(mListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
