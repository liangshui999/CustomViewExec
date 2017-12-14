package com.example.customviewexec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mImgOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mImgOne = (ImageView) findViewById(R.id.img_one);
        mImgOne.setOnClickListener(this);

        ViewGroup viewGroup = new ViewGroup(this) {

            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return super.dispatchTouchEvent(ev);
            }

            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_one:
                Toast.makeText(this, "点击了图片", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
