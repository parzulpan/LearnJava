package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 单例(Singleton)设计模式-懒汉式
 */

public class SingletonTest2 {
    public static void main(String[] args) {
        Singleton2 singleton2 = Singleton2.getInstance();
        Singleton2 singleton21 = Singleton2.getInstance();
        System.out.println(singleton2 == singleton21);
    }
}

class Singleton2 {

    // 1.
    private Singleton2() {

    }

    // 2.
    private static Singleton2 singleton2;

    // 3.
    public static Singleton2 getInstance() {
        if (singleton2 == null) {
            singleton2 = new Singleton2();
        }

        return singleton2;
    }

}
