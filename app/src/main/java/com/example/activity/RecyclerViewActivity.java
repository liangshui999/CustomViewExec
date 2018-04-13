package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adapter.MyRecyclerAdapter;
import com.example.bean.RecyclerModle;
import com.example.callback.MyOnItemClickListener;
import com.example.constant.ItemViewType;
import com.example.itemdecorations.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018-03-21 on 14:31
 * 作者：ls
 * RecyclerView的使用
 * compile 'com.android.support:recyclerview-v7:25.3.1'
 */

public class RecyclerViewActivity extends Activity implements View.OnClickListener{

    private RecyclerView recyclerView;

    private MyRecyclerAdapter adapter;

    private List<RecyclerModle> modles;

    private List<Integer> itemViewTypes;

    private Button btnAddHead;

    private Button btnAddTail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view);
        initView();
        modles = new ArrayList<>();
        itemViewTypes = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            if(i % 3 == 0){
                RecyclerModle modle = new RecyclerModle();
                modle.setName("aaaa");
                modle.setImgId(R.mipmap.ic_launcher);
                modles.add(modle);
            }else{
                RecyclerModle modle = new RecyclerModle();
                modle.setName("bb");
                modle.setImgId(R.mipmap.bz1);
                modles.add(modle);
            }
            itemViewTypes.add(ItemViewType.NORMAL);
        }

        adapter = new MyRecyclerAdapter(this, modles, itemViewTypes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(adapter);

        adapter.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        //WindowManager
        Glide.with(this);


    }

    private void initView() {
        btnAddHead = (Button) findViewById(R.id.btn_add_head);
        btnAddTail = (Button) findViewById(R.id.btn_add_bottom);
        btnAddHead.setOnClickListener(this);
        btnAddTail.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_head:
                itemViewTypes.add(ItemViewType.HEAD, 0);
                adapter.notifyItemChanged(0);
                break;
            case R.id.btn_add_bottom:
                Toast.makeText(this, "添加底部......", Toast.LENGTH_SHORT).show();
                itemViewTypes.add(ItemViewType.BOTTOM);
                adapter.notifyItemChanged(itemViewTypes.size() - 1);
                //adapter.notifyDataSetChanged();
                break;
        }
    }
}
