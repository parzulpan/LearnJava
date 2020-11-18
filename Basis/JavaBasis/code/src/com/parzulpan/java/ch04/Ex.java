package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc :
 */

public class Ex {

    public static void main(String[] args) {
        new A(new B());
    }
}
class A{
    public A(){
        System.out.println("A");
    }
    public A(B b){
        this();
        System.out.println("AB");
    }
}
class B{
    public B(){
        System.out.println("B");
    }
}