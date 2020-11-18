package com.parzulpan.java.ch03;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc :
 */

public class Test {
    public static void main(String[] args) {
        int[][] arr = new int[4][3];
        System.out.println(arr);    // 地址：[[I@2ff4f00f
        System.out.println(arr[0]);   // 地址：[I@2ff4f00f
        System.out.println(arr[0][0]);    // 0

        String[][] arr2 = new String[4][2];
        System.out.println(arr2);    // 地址：[[Ljava.lang.String;@3f0ee7cb
        System.out.println(arr2[0]);   // 地址：[Ljava.lang.String;@75bd9247
        System.out.println(arr2[0][0]);    // null

        String[][] arr3 = new String[4][];
        System.out.println(arr3);    // 地址：[[Ljava.lang.String;@7d417077
        System.out.println(arr3[0]);   // null
        System.out.println(arr3[0][0]);    // Error
    }
}
