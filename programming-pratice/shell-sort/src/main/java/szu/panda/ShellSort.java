package szu.panda;

import java.util.Arrays;

/**
 * @Author: Winnie
 * @Date: 2019-07-31
 * @Description: 希尔排序：对插入排序的改进，加入“步长”
 */
public class ShellSort {
    public static void main(String args[]) {
        int[] array = new int[]{2, 4, 1, 7, 9, 2, 6, 4, 17, 3, 79, 0, 47};
        ShellSort(array, array.length);
        System.out.println(Arrays.toString(array));
    }

    public static void ShellSort(int[] array, int length) {
        int step = length / 2;
        while (step > 0) {
            for (int i = 0; i < step; i++) {
                for (int j = i + step; j < length; ) {
                    //遍历i前面所有的数字
                    for (int k = j; k > 0; ) {
                        if (k - step < 0) break;
                        if (array[k] < array[k - step]) {
                            int temp = array[k];
                            array[k] = array[k - step];
                            array[k - step] = temp;
                        }
                        k = k - step;
                    }
                    j = j + step;
                }
            }
            step = step / 2;
        }
    }
}