package com.parzulpan.java.ch03;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 创建一个长度为6的int型数组，要求数组元素的值都在1-30之间，且是随机赋值。同时，要求元素的值各不相同。
 */

public class genArray {
    public static void main(String[] args) {
        int[] arr = new int[6];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)(Math.random() * 30) + 1;

            for (int j = 0; j < i; ++j) {
                if (arr[i] == arr[j]) {
                    --i;
                    break;
                }
            }
         }

        for (int value : arr) {
            System.out.println(value);
        }
    }
}
