package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 代码块练习
 */


class Father {
    static {
        System.out.println("11111111111");  // 1
    }

    {
        System.out.println("22222222222");  // 2
    }

    public Father() {
        System.out.println("33333333333");  // 3

    }

}

public class Son extends Father {
    static {
        System.out.println("44444444444");  // 4
    }

    {
        System.out.println("55555555555");  // 5
    }

    public Son() {
        System.out.println("66666666666");  // 6
    }

    public static void main(String[] args) { // 由父及子 静态先行
        System.out.println("77777777777");  // 7
        System.out.println("************************"); // 8
        new Son();  // 输出结果：1 -> 4 -> 7 -> 8 -> 2 -> 3 -> 5 -> 6
        System.out.println("************************");

        new Son();  // 输出结果：2 -> 3 -> 5 -> 6
        System.out.println("************************");
        new Father();   // 输出结果：2 -> 3
    }

}

