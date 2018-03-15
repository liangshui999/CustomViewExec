package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.activity.R;

import java.util.List;

/**
 * 创建日期：2017-12-14 on 14:26
 * 作者：ls
 */

public class DelItemAdapter extends BaseAdapter {

    private Context context;

    private List<String> items;

    private LayoutInflater layoutInflater;

    public DelItemAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if(v == null){
            v = layoutInflater.inflate(R.layout.item_del_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) v.findViewById(R.id.text_item_content);
            viewHolder.btnDel = (Button) v.findViewById(R.id.btn_delete);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) v.getTag();
        }

        //给view设置值
        viewHolder.textView.setText(items.get(position));
        return v;
    }

    class ViewHolder{
        TextView textView;
        Button btnDel;
    }
}
