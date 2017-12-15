package com.example.customviewexec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.adapter.DelItemAdapter;
import com.example.callback.OnDelBtnClickListener;
import com.example.widgets.SlideDeleteItemListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SlideDeleteItemListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mListView = (SlideDeleteItemListView) findViewById(R.id.list_del_items);
        final List<String> items = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            items.add("" + i);
        }
        final DelItemAdapter adapter = new DelItemAdapter(this, items);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setmOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onClick(int position) {
                items.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
