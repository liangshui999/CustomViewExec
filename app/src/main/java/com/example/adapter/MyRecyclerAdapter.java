package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activity.R;
import com.example.bean.RecyclerModle;
import com.example.callback.MyOnItemClickListener;
import com.example.constant.ItemViewType;

import java.util.List;

/**
 * 创建日期：2018-03-21 on 14:43
 * 作者：ls
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MyRecyclerAdapter";

    private Context mContext;
    private List<RecyclerModle> mDatas;
    private List<Integer> mItemTypes;
    private LayoutInflater mInflater;
    private MyOnItemClickListener myOnItemClickListener;

    public MyRecyclerAdapter(Context mContext, List<RecyclerModle> mDatas, List<Integer> mItemTypes) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemTypes = mItemTypes;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType){
            case ItemViewType.HEAD:
                itemView = mInflater.inflate(R.layout.recycler_item_head, parent, false);
                break;
            case ItemViewType.NORMAL:
                itemView = mInflater.inflate(R.layout.recycler_item, parent, false);
                break;
            case ItemViewType.BOTTOM:
                itemView = mInflater.inflate(R.layout.recycler_item_bottom, parent, false);
                break;
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = mItemTypes.get(position);
        switch (viewType){
            case ItemViewType.HEAD:
                break;
            case ItemViewType.NORMAL:
                int index = 0;
                for(int i = 0; i < mItemTypes.size(); i++){
                    if(mItemTypes.get(i) == ItemViewType.NORMAL){
                        index = i;
                        break;
                    }
                }
                ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.img_name);
                TextView textView = (TextView) holder.itemView.findViewById(R.id.text_name);
                imageView.setImageResource(mDatas.get(position - index).getImgId());
                textView.setText(mDatas.get(position - index).getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myOnItemClickListener.OnItemClick(v, position);
                    }
                });
                break;
            case ItemViewType.BOTTOM:
                break;
        }

    }

    /**
     * 这个必须复写
     * 这个函数很重要，务必复写正确
     * 是告诉有多少条view的
     */
    @Override
    public int getItemCount() {
        return mItemTypes.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(mItemTypes != null){
            return mItemTypes.get(position);
        }
        return super.getItemViewType(position);
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }


}
