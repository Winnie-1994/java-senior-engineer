package szu.panda;

import java.util.Arrays;

/**
 * @Author: Winnie
 * @Date: 2019-07-25
 * @Description:
 */
public class InsertSort {
    public static void main(String args[]){
        int[] array = new int[]{2,4,1,7,9,2,6,4,17,3};
        insertSort(array, array.length);
        System.out.println(Arrays.toString(array));

    }

    public static void insertSort(int[] array, int length){
        //遍历所有数字
        for(int i = 1; i < length; i++){
            //遍历i前面所有的数字
            for(int j = i; j > 0; j--){
                if(array[j] < array[j-1]){
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
        }

    }
}