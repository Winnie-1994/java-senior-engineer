package szu.panda;

/**
 * @Author: Winnie
 * @Date: 2019-07-29
 * @Description: 二分法查找，基于已排序的数组进行查找
 */
public class TestBinarySearch {

    public static void main(String args[]){
        int[] array = new int[]{1,2,3,4,5,6,7,8,9};
        int target = 7;
        int start = 0;
        int end = array.length-1;
        int index = -1;
        int mid = (start + end)/2;
        while (true){
            if(array[mid] == target) {
                index = mid;
                break;
            } else {
                if (array[mid] < target){
                    start = mid +1;
                } else {
                    end = mid -1;
                }
                mid = (start + end)/2;
            }
        }

        System.out.println("index: " + index);

    }
}