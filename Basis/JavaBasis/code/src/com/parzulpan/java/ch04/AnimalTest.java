package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-18
 * @Desc : 对象的创建和使用
 */

public class AnimalTest {
    public static void main(String[] args) {
        Animal an = new Animal();   // 创建对象
        an.legs = 4;    // 访问属性
        System.out.println(an.legs);
        an.eat();   // 访问方法
        an.move();  // 访问方法
    }
}

// 设计类
class Animal {
    public int legs;

    public void eat() {
        System.out.println("Eating.");
    }

    public void move() {
        System.out.println("Move.");
    }

}
