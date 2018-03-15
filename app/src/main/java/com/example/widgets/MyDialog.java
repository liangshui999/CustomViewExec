package com.example.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.activity.R;

/**
 * 创建日期：2018-03-15 on 10:38
 * 作者：ls
 */

public class MyDialog extends Dialog {

    private LayoutInflater inflater;

    private RelativeLayout rl;

    private TextView textMsg;

    public MyDialog(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        rl = (RelativeLayout) inflater.inflate(R.layout.dialog_contact, null);
        textMsg = (TextView) rl.findViewById(R.id.text_msg);
        textMsg.setAlpha(0.4f);
        setContentView(rl);
        getWindow().getAttributes().dimAmount = 0f;
    }

    public void setMsg(String msg){
        textMsg.setText(msg);
    }


}
