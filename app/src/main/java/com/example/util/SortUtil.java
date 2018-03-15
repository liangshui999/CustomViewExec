package com.example.util;

/**
 * 创建日期：2018-02-26 on 9:15
 * 作者：ls
 */

public class SortUtil {

    /**
     * 快速排序
     * @param arr 待排序的数组
     * @param start 排序的起始位置
     * @param end 排序的终点位置 len - 1
     */
    public static void fastSort(int[] arr, int start, int end){
        int len = end + 1;
        if(len == 0){
            return;
        }
        int i = start; //头部的小兵
        int j = len - 1; //尾部的小兵
        while(true){
            //尾部的小兵先走
            while(true){
                if(arr[j] < arr[start] || i == j){
                    break;
                }
                j--;
            }

            //头部的小兵再走
            while(true){
                if(arr[i] > arr[start] || i == j){
                    break;
                }
                i++;
            }

            //判断头部的小兵和尾部的小兵是否相遇了
            if(i == j){ //相遇了
                swap(arr, start, i); //把i（j和i相同）的值和start处的值交换
                if(i - 1 > start){
                    fastSort(arr, start, i - 1);
                }
                if(i + 1 < end){
                    fastSort(arr, i + 1, end);
                }
                break;
            }else{ //没有相遇,交换i和j对应位置的值
                swap(arr, i, j);
            }
        }
    }


    /**
     * 交换
     */
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    /**
     * 二分查找，循环的方式
     * @param arr 待查找的数组
     * @param start 起始位置
     * @param end 终点位置
     * @param key 要查找的元素
     * @return 查找到的索引
     */
    public static int binarySearch(int[] arr, int start, int end, int key){
        while(start <= end){
            int mid = (start + end) / 2;
            int midVal = arr[mid];
            if(key < midVal){
                end = mid - 1;
            }else if(key > midVal){
                start = mid + 1;
            }else{
                return mid;
            }
        }
        return -1;
    }


    /**
     * 二分查找，递归的方式
     * @param arr 待查找的数组
     * @param start 起始位置
     * @param end 终点位置
     * @param key 要查找的元素
     * @return 查找到的索引
     */
    public static int binarySearch2(int[] arr, int start, int end, int key){
        int mid = (start + end) / 2;
        int midVal = arr[mid];
        if(key < midVal){
            end = mid - 1;
        }else if(key > midVal){
            start = mid + 1;
        }else{
            return mid;
        }
        return binarySearch2(arr, start, end, key);
    }



}
