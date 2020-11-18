package com.parzulpan.java.ch03;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 二分查找
 */

public class BinaryFind {

    public static boolean binaryFind(int[] arr, int number) {
        boolean isFlag = false;
        int start = 0, end = arr.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (arr[mid] == number) {
                isFlag = true;
                break;
            } else if (arr[mid] > number) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return isFlag;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{-99, -54, -2, 0, 2, 33, 43, 256, 999};
        System.out.println(binaryFind(arr, 256));
        System.out.println(binaryFind(arr, 1342));
    }
}
