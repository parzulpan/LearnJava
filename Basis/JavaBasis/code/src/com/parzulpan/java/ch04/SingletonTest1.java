package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-21
 * @Desc : 单例(Singleton)设计模式-饿汉式
 */

public class SingletonTest1 {
    public static void main(String[] args) {
        Singleton1 singleton1 = Singleton1.getInstance();
        Singleton1 singleton11 = Singleton1.getInstance();
        System.out.println(singleton1 == singleton11);
    }
}

//
class Singleton1 {

    // 1. 私有化构造器
    private Singleton1() {

    }

    // 2. 内部提供一个当前类的实例，且为静态
    private static Singleton1 singleton1;

    // 3. 提供公共的静态方法，返回当前类的对象
    public static Singleton1 getInstance() {
        return singleton1;
    }
}
