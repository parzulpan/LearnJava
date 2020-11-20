package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : 多态的使用举例一
 */

public class PolymorphismTest {
    public static void main(String[] args) {
        PolymorphismTest polymorphismTest = new PolymorphismTest();
        polymorphismTest.func(new Cat());   //
        polymorphismTest.func(new Dog());   //

    }

    public void func(AnimalA animalA) {
        animalA.eat();
        animalA.shout();
    }
}

class AnimalA {
    public void eat() {
        System.out.println("动物：进食！");
    }

    public void shout() {
        System.out.println("动物：叫！");
    }
}

class Cat extends AnimalA {

    public void eat() {
        System.out.println("猫：进食！");
    }

    public void shout() {
        System.out.println("猫：叫！");
    }

}

class Dog extends AnimalA {
    public void eat() {
        System.out.println("狗：进食！");
    }

    public void shout() {
        System.out.println("狗：叫！");
    }
}