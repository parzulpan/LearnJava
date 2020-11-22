package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 抽象类举例
 */

public class AbstractTest {
    public static void main(String[] args) {
        AA aa = new BB();
        aa.m1();
        aa.m2();
    }
}

abstract class AA {
    abstract void m1();

    public void m2() {
        System.out.println("A类中定义的m2方法");
    }
}

class BB extends AA {
    @Override
    void m1() {
        System.out.println("B类中定义的m1方法");
    }

    @Override
    public void m2() {
        System.out.println("B类中定义的m2方法");
    }
}
