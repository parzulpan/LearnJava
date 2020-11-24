package com.parzulpan.java;

import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : String 常用方法
 */

public class StringMethodTest {

    @Test
    public void test1() {
        String s1 = "HelloWorld";
        System.out.println(s1.length());
        System.out.println(s1.charAt(0));
//        System.out.println(s1.charAt(10));  // StringIndexOutOfBoundsException

        System.out.println(s1.isEmpty());

        String s2 = s1.toLowerCase();
        String s3 = s1.toUpperCase();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        String s4 = "  Hello World   ";
        String s5 = s4.trim();
        System.out.println("---" + s4 + "---");
        System.out.println("---" + s5 + "---");

        String s6 = "HelloWorld";
        String s7 = "helloworld";
        System.out.println(s6.equals(s7));
        System.out.println(s6.equalsIgnoreCase(s7));

        String s8 = "Hello";
        String s9 = s8.concat(" World");
        System.out.println(s9);

        String s10 = "abc";
        String s11 = new String("abe");
        System.out.println(s10.compareTo(s11)); // -2

        String s12 = "Beijing China";
        String s13 = s12.substring(1);
        String s14 = s12.substring(8, 13);
        System.out.println(s13);    // eijing China
        System.out.println(s14);    // China
    }

    @Test
    public void test2() {
        String s1 = "HelloWorld@!";

        boolean b1 = s1.endsWith("@!");
        System.out.println(b1);

        boolean b2 = s1.startsWith("He");
        System.out.println(b2);

        boolean b3 = s1.startsWith("ll", 2);
        System.out.println(b3);


        String s3 = "HelloWorldHelloWorld";
        System.out.println(s3.contains("ll"));

        System.out.println(s3.indexOf("ll"));   // 2
        System.out.println(s3.indexOf("AF"));   // -1
        System.out.println(s3.indexOf("ll",4));   // 12

        System.out.println(s3.lastIndexOf("ll"));   // 12
        System.out.println(s3.lastIndexOf("AF"));   // -1
        System.out.println(s3.lastIndexOf("ll", 14));   // 12
        System.out.println(s3.lastIndexOf("ll", 10));   // 2

        String s4 = s3.replace('W', '@');
        System.out.println(s4);

        String s5 = s3.replace("He", "@@");
        System.out.println(s5);
    }

    @Test
    public void test3() {
        String str = "12hello34world5java7891mysql456";
        String string = str.replaceAll("\\d+", ",").replaceAll("^,|,$", "");
        System.out.println(string);

        String str1 = "12345";
        boolean matches = str1.matches("\\d+");
        System.out.println(matches);

        String tel = "0571-4534289";
        boolean result = tel.matches("0571-\\d{7,8}");
        System.out.println(result);

        String str2 = "hello|world|java";
        String[] str4 = str2.split("\\|");
        for (int i = 0; i < str4.length; i++) {
            System.out.println(str4[i]);
        }

        System.out.println();

        String str3 = "hello.world.java";
        String[] strs3 = str3.split("\\.");
        for (int i = 0; i < strs3.length; i++) {
            System.out.println(strs3[i]);
        }

    }
}
