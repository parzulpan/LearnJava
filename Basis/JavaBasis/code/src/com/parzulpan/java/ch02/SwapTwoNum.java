package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 实现两个变量值交换？
 */

public class SwapTwoNum {
    public static void main(String[] args) {
        swapV1(324, 953);
        swapV2(324, 953);
        swapV3(324, 953);
    }

    public static void swapV1(int num1, int num2) {
        int temp = num1;
        num1 = num2;
        num2 = temp;
        System.out.println("num1 =  " + num1 + " num2 = " + num2);
    }

    public static void swapV2(int num1, int num2) {
        num1 = num1 + num2;
        num2 = num1 - num2;
        num1 = num1 - num2;
        System.out.println("num1 =  " + num1 + " num2 = " + num2);
    }

    public static void swapV3(int num1, int num2) {
        num1 = num1 ^ num2;
        num2 = num1 ^ num2;
        num1 = num1 ^ num2;
        System.out.println("num1 =  " + num1 + " num2 = " + num2);
    }
}
