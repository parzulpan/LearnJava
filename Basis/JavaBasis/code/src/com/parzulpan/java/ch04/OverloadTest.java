package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 方法的重载
 */

public class OverloadTest {
    public static void main(String[] args) {
        System.out.println(max(1, 2));
        System.out.println(max(1.2, 2.1));
        System.out.println(max(1.2, 3.1, 0.1));

    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static double max(double a, double b) {
        return a > b ? a : b;
    }

    public static double max(double a, double b, double c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }
}
