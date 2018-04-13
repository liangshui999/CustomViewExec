package com.example.activity.designPatten;

import com.example.util.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018-03-27 on 9:37
 * 作者：ls
 */

public class MyDocument implements Cloneable{

    private int a;

    private List<String> strs;

    public MyDocument(){
        MyLog.log("MyDocument............");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MyDocument result = (MyDocument) super.clone();
        ArrayList<String> temp = (ArrayList<String>) strs;
        result.setStrs((List<String>) temp.clone());
        result.setA(a);
        return result;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public List<String> getStrs() {
        return strs;
    }

    public void setStrs(List<String> strs) {
        this.strs = strs;
    }

    @Override
    public String toString() {
        return "MyDocument{" +
                "a=" + a +
                ", strs=" + strs +
                '}';
    }
}
