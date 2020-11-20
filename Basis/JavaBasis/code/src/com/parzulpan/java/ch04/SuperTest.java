package com.parzulpan.java.ch04;

import java.util.Date;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc :
 */

public class SuperTest {
    public static void main(String[] args) {

    }
}


class SuperPerson {
    private String name;
    private int age;
    private Date birthDate;

    public SuperPerson(String name, int age, Date d) {
        this.name = name;
        this.age = age;
        this.birthDate = d;
    }
    public SuperPerson(String name, int age) {
        this(name, age, null);
    }
    public SuperPerson(String name, Date d) {
        this(name, 30, d);
    }
    public SuperPerson(String name) {
        this(name, 30);
    }

}

class SuperStudent extends SuperPerson {
    private String school;
    public SuperStudent(String name, int age, String s) {
        super(name, age);
        school = s;
    }

    public SuperStudent(String name, String s) {
        super(name);
        school = s;
    }

    // 编译出错: no super()，系统将调用父类无参数的构造器。
//    public SuperStudent(String s) {
//        school = s;
//    }

}
