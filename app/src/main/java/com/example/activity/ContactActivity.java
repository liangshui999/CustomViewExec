package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.adapter.ContactAdapter;
import com.example.widgets.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 联系人的activity
 * 创建日期：2018-03-14 on 13:42
 * 作者：ls
 */

public class ContactActivity extends Activity {

    private ListView listView;
    private SideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = (ListView) findViewById(R.id.list_contact);
        sideBar = (SideBar) findViewById(R.id.side_bar);
        List<String> names = new ArrayList<>();
        names.add("zmm");
        names.add("Zkkj");
        names.add("ajjj");
        names.add("bljj");
        names.add("bljj");
        names.add("cklkk");
        names.add("djjjj");
        names.add("Cjjjj");
        names.add("dkkkjjj");
        names.add("Ekkk");
        names.add("fkkk");
        names.add("ikkk");
        names.add("jkkk");
        names.add("llkkkk");
        names.add("nkkk");
        names.add("mkk");
        names.add("okk");
        names.add("pk");
        names.add("qk");
        names.add("sk");
        names.add("rk");
        names.add("tk");
        names.add("wk");
        names.add("uk");
        names.add("xxxz");
        names.add("yq");
        names.add("zzz");
        names.add("zser");
        names.add("fk");
        names.add("ykkk");
        names.add("nkkk");
        ContactAdapter adapter = new ContactAdapter(this, names);
        listView.setAdapter(adapter);

        sideBar.setmListView(listView);
    }


}
