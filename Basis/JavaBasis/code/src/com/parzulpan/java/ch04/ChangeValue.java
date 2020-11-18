package com.parzulpan.java.ch04;

import java.io.PrintStream;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 某公司的笔试题，实际考察的是
 */

public class ChangeValue {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        method(a, b);   // 需要在 method 方法被调用之后，仅打印出 a = 100, b = 200，请写出 method 方法的代码
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }


//    // 方法一：函数执行完退出
//    public static void method(int a, int b) {
//        a *= 10;
//        b *= 20;
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
//        System.exit(0);
//    }

    // 方法二：重写
    public static void method(int a, int b) {
        PrintStream printStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                if ("a = 10".equals(x)) {
                    x = "a = 100";
                } else if ("b = 10".equals(x)) {
                    x = "b = 200";
                }
                super.println(x);
            }
        };

        System.setOut(printStream);
    }

}
