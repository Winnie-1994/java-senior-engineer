package szu.panda;

import java.util.Arrays;

/**
 * @Author: Winnie
 * @Date: 2019-07-31
 * @Description: 选择排序：选择最小的数往前排
 */
public class SelectSort {
    public static void main(String args[]){
        int[] array = new int[]{2, 4, 1, 7, 9, 2, 6, 4, 17, 3, 79, 0, 47};
        selectSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void selectSort(int[] array){
        for(int i=0; i<array.length; i++){
            int min = array[i];
            int temp = i;
            for(int j=i+1; j<array.length; j++){
                if(array[j] < min){
                    min = array[j];
                    temp = j;
                }
            }
            array[temp] = array[i];
            array[i] = min;
        }
    }
}