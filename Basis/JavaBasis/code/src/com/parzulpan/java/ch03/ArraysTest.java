package com.parzulpan.java.ch03;

import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : Arrays 工具类的常用方法
 */

public class ArraysTest {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4};
        int[] arr2 = new int[]{1, 3, 2, 4};

        System.out.println(Arrays.equals(arr1, arr2));

        System.out.println(Arrays.toString(arr1));

        int[] arr3 = new int[]{1, 2, 3, 4};
        Arrays.fill(arr3, 10);
        System.out.println(Arrays.toString(arr3));

        Arrays.sort(arr2);
        System.out.println(Arrays.toString(arr2));

        int[] arr4 = new int[]{-99, -54, -2, 0, 2, 33, 43, 256, 999};
        int index = Arrays.binarySearch(arr4, 239);
        if (index < 0) {
            System.out.println("arr4 中不存在 " + 239);
        }
    }
}
