package com.example.messengers;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.constant.MessengerConstant;

/**
 * 创建日期：2018-01-02 on 15:30
 * 作者：ls
 */

public class RemoteService extends Service {

    private static final String TAG = "RemoteService";

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MessengerConstant.TAG_ONE:
                    //接收客户端发过来的消息
                    Bundle bundle = msg.getData();
                    String fromClient = bundle.getString(MessengerConstant.KEY_ONE);
                    Log.d(TAG, "fromClient = " + fromClient);
                    //向客户端回复消息
                    Messenger messenger1 = msg.replyTo;//向客户端回消息，必须使用客户端的Messenger
                    Message msg1 = Message.obtain();
                    msg1.what = MessengerConstant.TAG_TWO;
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(MessengerConstant.KEY_TWO, "来自服务端的回复");
                    msg1.setData(bundle1);
                    msg1.replyTo = mMessenger;//把服务端的Messenger带给客户端
                    try {
                        messenger1.send(msg1);//使用客户端的Messenger向客户端发送消息
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case MessengerConstant.TAG_THREE:
                    Log.d(TAG, msg.getData().getString(MessengerConstant.KEY_THREE));
                    break;
            }
        }
    }

    private Messenger mMessenger = new Messenger(new MyHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind.........");
        return mMessenger.getBinder();
    }
}
