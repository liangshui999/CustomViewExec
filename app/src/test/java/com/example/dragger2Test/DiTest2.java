package com.example.dragger2Test;

import com.example.dragger2Test.subComponent.NeedDi;
import com.example.util.MyLog;

import org.junit.Test;

import javax.inject.Inject;

public class DiTest2 {


    @Test
    public void test1(){
        NeedDi needDi = new NeedDi();
        MyLog.log("needDi.a1 = " + needDi.a1);
        MyLog.log("needDi.b1 = " + needDi.b1);
        MyLog.log("needDi.b2 = " + needDi.b2);
    }


}
