package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 质数：只能被 1 和它本身整除的自然数。
 */

public class GetPrimeNumber {
    public static void main(String[] args) {
        int primeNumberCnt = 0;
        long start = System.currentTimeMillis();
        for (int i = 2; i <= 100000; ++i) {
            boolean isFlag = true;

            for (int j = 2; j < Math.sqrt(i); ++j) {    // 优化2：只对本身是质数的自然数是有效的
                if(i % j == 0) {
                    isFlag = false;
                    break;  // 优化1：只对本身是非质数的自然数是有效的
                }
            }

            if (isFlag) {
                ++primeNumberCnt;
//                System.out.println(i);
            }
        }

        System.out.println("primeNumberCnt: " + primeNumberCnt);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }
}
