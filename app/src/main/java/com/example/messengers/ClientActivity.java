package com.example.messengers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.constant.MessengerConstant;

import java.lang.ref.SoftReference;

/**
 * 创建日期：2018-01-02 on 15:45
 * 作者：ls
 */

public class ClientActivity extends Activity {

    private static final String TAG = "ClientActivity";

    class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MessengerConstant.TAG_TWO:
                    Log.d(TAG, "fromService = " + msg.getData().getString(MessengerConstant.KEY_TWO));

                    Messenger messenger = msg.replyTo;//取出服务端的Messenger
                    Bundle bundle = new Bundle();
                    bundle.putString(MessengerConstant.KEY_THREE, "客户端再次问候服务器");
                    Message ms = Message.obtain();
                    ms.what = MessengerConstant.TAG_THREE;
                    ms.setData(bundle);
                    ms.replyTo = mMessenger;//把客户端的Messenger带给服务端
                    try {
                        messenger.send(ms);//使用的是服务端的Messenger发送消息
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 别人要给我发消息，必须使用我的Messenger
     * msg.replyTo = mMessenger
     */
    private Messenger mMessenger = new Messenger(new MyHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Messenger messenger = new Messenger(service);//获取到服务端的Messenger
                Message msg = Message.obtain();
                msg.what = MessengerConstant.TAG_ONE;
                Bundle bundle = new Bundle();
                bundle.putString(MessengerConstant.KEY_ONE, "这是来自客户端的问候");
                msg.setData(bundle);
                msg.replyTo = mMessenger;//把我的Messenger通过消息带给服务端
                try {
                    messenger.send(msg);//注意发消息所使用的messenger和replyTo里面是不一样的
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }
}
