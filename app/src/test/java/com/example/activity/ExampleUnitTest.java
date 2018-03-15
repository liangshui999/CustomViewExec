package com.example.activity;

import com.example.util.SortUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1(){
        int[] arr = {1, 5, 9, 18, 99, 158, 199};
        /*SortUtil.fastSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));*/
        int result = SortUtil.binarySearch2(arr, 0, arr.length - 1, 9);
        System.out.println(result);
        //HashMap
    }
}