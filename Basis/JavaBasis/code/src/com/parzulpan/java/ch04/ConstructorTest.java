package com.parzulpan.java.ch04;

/**
 * @Author : parzulpan
 * @Time : 2020-11-19
 * @Desc : 构造器重载
 */

public class ConstructorTest {
    public static void main(String[] args) {
//        StudentA s1 = new StudentA(); // Error
        StudentA s2 = new StudentA("A", 11);
        s2.printInfo();
        StudentA s3 = new StudentA("A", 11, "B");
        s3.printInfo();
        StudentA s4 = new StudentA("A", 11, "B", "C");
        s4.printInfo();
    }
}


class StudentA {
    String name;
    int age;
    String school;
    String major;

    StudentA(String n, int a) {
        name = n;
        age = a;
    }

    StudentA(String n, int a, String s) {
        name = n;
        age = a;
        school = s;
    }

    StudentA(String n, int a, String s, String m) {
        name = n;
        age = a;
        school = s;
        major = m;
    }

    public void printInfo() {
        System.out.println(name + " " + age + " " + school + " " + major);
    }
}