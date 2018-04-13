package com.example.activity.designPatten;

import android.os.Environment;

import com.example.util.MyLog;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018-03-27 on 9:42
 * 作者：ls
 */

public class MyDocumentTet {

    @Test
    public void test1(){
        List<String> strs = new ArrayList<>();
        strs.add("aaa");
        strs.add("bbb");
        strs.add("ccc");
        MyDocument original = new MyDocument();
        original.setStrs(strs);
        original.setA(17);
        try {
            //并没有调用构造函数,默认情况下是浅拷贝
            MyDocument copy = (MyDocument) original.clone();
            MyLog.log("copy = " + copy);
            copy.getStrs().add("哈哈哈.....");
            MyLog.log("original = " + original);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2(){
        MyLog.log(Environment.getDataDirectory());
    }

}
