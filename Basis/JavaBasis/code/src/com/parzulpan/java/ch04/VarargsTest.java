package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 可变个数的参数
 */

public class VarargsTest {
    public static void main(String[] args) {
        Varargs varargs = new Varargs();
        varargs.test1();    // C
        varargs.test1("a", "b");    // C
        varargs.test1("cc");    // B
        varargs.test(new String[]{"aa"});  // A
    }


}

class Varargs {
    // A
    public void test(String[] msg) {
        System.out.println("含字符串数组参数的test方法!");
    }

    // B
    public void test1(String book) {
        System.out.println("与可变形参方法构成重载的test1方法!");
    }

    // C
    public void test1(String ... books) {
        System.out.println("形参长度可变的test1方法!");
    }
}
