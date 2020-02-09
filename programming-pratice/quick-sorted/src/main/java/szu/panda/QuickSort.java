package szu.panda;

import java.util.Arrays;

/**
 * @Author: Winnie
 * @Date: 2019-07-16
 * @Description: 快速排序
 */
public class QuickSort {

    public static void main(String args[]) {
        int[] array = new int[]{3, 2, 4, 1, 1, 4, 7, 8, 59, 65, 1, 0, 60, 59, 102, 0};
        quickSort(array, 0, (array.length - 1));
        System.out.println(Arrays.toString(array));
    }

    private static void quickSort(int[] array, int low, int high) {
        //进行排序的条件
        if(low < high){
            int start = low;
            int end = high;
            //把array[start]作为标准数
            int index = array[start];
            //循环找比标准数大的数和比标准数小的数
            while (start < end) {
                //判断右边的数是否比标准数大
                while (start < end && index <= array[end]) {
                    end--;
                }
                //若右边的数比标准数小，使用右边的数替换左边的数
                if(start < end){
                    array[start++] = array[end];
                }
                //判断左边的数是否比标准数小
                while (start < end && index > array[start]) {
                    start++;
                }
                //若左边的数比标准数大，使用左边的数替换右边的数
                if(start < end){
                    array[end--] = array[start];
                }
            }
            array[start] = index;
            //把所有小的数再处理一次
            quickSort(array, low, start-1);
            //把所有大的数再处理一次
            quickSort(array, start+1, high);
        }

    }
}