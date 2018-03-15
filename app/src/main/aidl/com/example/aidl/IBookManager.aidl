// IBookManager.aidl
package com.example.aidl;
import com.example.aidl.Book;
import com.example.aidl.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();

    //不要少了前面的in
    void addBook(in Book book);

    void registerBookArriveListener(in IOnNewBookArrivedListener listener);

    void unRegisterBookArriveListener(in IOnNewBookArrivedListener listener);
}
