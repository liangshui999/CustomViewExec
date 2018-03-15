package com.example.activity;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建日期：2018-01-04 on 15:40
 * 作者：ls
 */

public class CountDownLatchTest {

    @Test
    public void test1() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);//注意这里初始是2
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                log("非主线程1中的代码执行了");
                //因为初始是2，所以这里减了2次才放行
                countDownLatch.countDown();
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();//CountDownLatch中的数字减到0，这个方法就会放行
        log("方法结束了");
    }

    private void log(Object o){
        System.out.println(o);
    }

}
