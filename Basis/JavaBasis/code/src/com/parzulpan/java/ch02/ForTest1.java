package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : for语句例题1
 * 输入两个正整数 m 和 n，求其最大公约数和最小公倍数。
 * 比如：12 和 20 的最大公约数是 4，最小公倍数是 60。
 */

public class ForTest1 {
    public static void main(String[] args) {
        int m = 12, n = 20;
        int max = Math.max(m, n);
        int min = Math.min(m, n);

        for (int i = min; i >= 1; --i) {
            if (m % i == 0 && n % i == 0) {
                System.out.println("最大公约数：" + i);
                break;
            }
        }

        for (int i = max; i <= m * n; ++i) {
            if (i % m == 0 && i % n == 0) {
                System.out.println("最小公倍数：" + i);
                break;
            }
        }
    }
}
