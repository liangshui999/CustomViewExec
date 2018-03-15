package com.example.aidl;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 创建日期：2018-01-04 on 9:16
 * 作者：ls
 * 注意这个类的对象，会在多线程环境下执行
 * 务必要注意线程安全
 */

public class BookManager extends IBookManager.Stub {

    private static final String TAG = "BookManager";

    private List<Book> mBooks;

    /**
     * 这个类本身就是线程安全的
     */
    private RemoteCallbackList<IOnNewBookArrivedListener> mIOnNewBookArrivedListeners;

    public BookManager(){
        mBooks = new CopyOnWriteArrayList<>();
        mBooks.add(new Book(1, "红楼梦"));
        mBooks.add(new Book(2, "三国演义"));
        mIOnNewBookArrivedListeners = new RemoteCallbackList<>();
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        return mBooks;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Log.e(TAG, "book = " + book);
        mBooks.add(book);
        Log.e(TAG, "mBooks = " + mBooks);
        //通知所有的监听器
        int size = mIOnNewBookArrivedListeners.beginBroadcast();
        for(int i = 0; i < size; i++){
            IOnNewBookArrivedListener listener = mIOnNewBookArrivedListeners.getBroadcastItem(i);
            listener.onNewBookArrive(book);
        }
        mIOnNewBookArrivedListeners.finishBroadcast();
    }

    @Override
    public void registerBookArriveListener(IOnNewBookArrivedListener listener) throws RemoteException {
        Log.e(TAG, "registerBookArriveListener...........listener = " + listener);
        Log.e(TAG, "registerBookArriveListener...........listener.asBinder() = " + listener.asBinder());
        mIOnNewBookArrivedListeners.register(listener);
    }

    @Override
    public void unRegisterBookArriveListener(IOnNewBookArrivedListener listener) throws RemoteException {
        Log.e(TAG, "unRegisterBookArriveListener...........listener = " + listener);
        Log.e(TAG, "unRegisterBookArriveListener...........listener.asBinder() = " + listener.asBinder());
        int size = mIOnNewBookArrivedListeners.beginBroadcast();
        Log.e(TAG, "注销之前.....size = " + size);
        mIOnNewBookArrivedListeners.finishBroadcast();

        mIOnNewBookArrivedListeners.unregister(listener);//注销

        size = mIOnNewBookArrivedListeners.beginBroadcast();
        Log.e(TAG, "注销之后.....size = " + size);
        mIOnNewBookArrivedListeners.finishBroadcast();
    }
}
