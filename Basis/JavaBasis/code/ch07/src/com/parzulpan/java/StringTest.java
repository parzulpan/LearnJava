package com.parzulpan.java;

import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : String 的使用
 */

public class StringTest {
    public static void main(String[] args) {

    }

    // 理解不可变性
    @Test
    public void test1() {
        String s1 = "abc";  //  字面量的定义方式
        String s2 = "abc";

        System.out.println(s1 == s2);   // true，比较地址值，
        // 说明 通过字面量的方式（区别于 new）给一个字符串赋值，
        // 此时的字符串值声明在方法区的字符串常量池中，而常量池不会存储相同内容的字符串。

        s1 = "hello";
        System.out.println(s1 == s2);   // false，
        // 体现了不可变性的第一点，对字符串重新赋值时，需要重新指定内存区域赋值

        String s3 = s2 + "def";
        System.out.println(s3 == s2);   // false，
        // 体现了不可变性的第二点，对现有字符串进行连接操作时，需要重新指定内存区域赋值

        String s4 = "abc";
        String s5 = s4.replace('a', 'c');
        System.out.println(s4);
        System.out.println(s5);
        // 体现了不可变性的第三点，对现有字符串进行修改操作时，需要重新指定内存区域赋值
    }

    // String 对象的创建
    @Test
    public void test2() {
        String s1 = "Java"; // 字面量的定义方式，在方法区的字符串常量池中
        String s2 = "Java";

        String s3 = new String("Java"); // new + 构造器的定义方式，在堆空间中
        String s4 = new String("Java");

        System.out.println(s1 == s2);   // true
        System.out.println(s1 == s3);   // false
        System.out.println(s1 == s4);   // false
        System.out.println(s3 == s4);   // false

        System.out.println("---------");

        Person p1 = new Person("Tom", 12);
        Person p2 = new Person("Tom", 12);

        System.out.println(p1.name.equals(p2.name));    // true
        System.out.println(p1.name == p2.name); // true

        p1.name = "Jerry";
        System.out.println(p2.name);    // Tom，不可变性
    }

    // String 的特性
    @Test
    public void test3() {

        String s1 = "hello";
        String s2 = "world";
        String s3 = "hello" + "world";
        String s4 = s1 + "world";
        String s5 = s1 + s2;
        String s6 = (s1 + s2).intern();

        System.out.println(s3 == s4);   // false
        System.out.println(s3 == s5);   // false
        System.out.println(s4 == s5);   // false
        System.out.println(s3 == s6);   // true

        // 1. 常量和常量的拼接结果在常量池，且常量池不会存在相同内容的常量
        // 2. 只要其中有一个是变量，结果就在堆中
        // 3. 如果拼接的结果调用 intern() 方法，返回值就在常量池中

    }
}
