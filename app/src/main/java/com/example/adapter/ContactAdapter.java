package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.activity.R;
import com.example.util.NameUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建日期：2018-03-14 on 13:48
 * 作者：ls
 */

public class ContactAdapter extends BaseAdapter implements SectionIndexer{

    private Context context;
    private List<String> names;
    private LayoutInflater layoutInflater;

    public ContactAdapter(Context context, List<String> names) {
        this.context = context;
        this.names = names;
        layoutInflater = LayoutInflater.from(context);
        //对名字进行排序，本程序只支持英文，如果需要中文的话
        //可以使用Pinyin4j类库对中文姓名进行处理
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                s1 = s1.toUpperCase();
                s2 = s2.toUpperCase();
                return s1.compareTo(s2);
            }
        });
    }


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if(v == null){
            v = layoutInflater.inflate(R.layout.list_item_contact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textIndex = (TextView) v.findViewById(R.id.text_index);
            viewHolder.textName = (TextView) v.findViewById(R.id.text_name);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) v.getTag();
        }
        //获取该位置的名字的首字母的大写
        String catalog = NameUtil.getCatalog(names.get(position));
        if(position == 0){
            viewHolder.textIndex.setVisibility(View.VISIBLE);
            viewHolder.textIndex.setText(catalog);
        }else{
            //获取该位置的前一个位置的首字母的大写
            String previousCatalog = NameUtil.getCatalog(names.get(position - 1));
            if(previousCatalog.equals(catalog)){
                viewHolder.textIndex.setVisibility(View.GONE);
            }else{
                viewHolder.textIndex.setVisibility(View.VISIBLE);
                viewHolder.textIndex.setText(catalog);
            }
        }
        viewHolder.textName.setText(names.get(position));
        return v;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }


    @Override
    public int getPositionForSection(int sectionIndex) {
        for(int i = 0; i < names.size(); i++){
            if(names.get(i).toUpperCase().charAt(0) == sectionIndex){
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView textIndex;
        TextView textName;
    }
}
