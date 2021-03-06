package com.parzulpan.java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : String 与 其他数据类型的相互转换
 */

public class StringTest1 {

    // String -> 基本数据类型、包装类
    @Test
    public void test1() {
        String s1 = "123";
//        int num = (int) s1; // ERROR
        int num = Integer.parseInt(s1);
        System.out.println(num);

        double num1 = Double.parseDouble(s1);
        System.out.println(num1);
    }

    // 基本数据类型、包装类 -> String
    @Test
    public void test2() {
        String s= "123";
        int num = 123;

        String s1 = String.valueOf(num);
        System.out.println(s1);
        String s2 = num + "";
        System.out.println(s2);

        System.out.println(s == s1);    // false
        System.out.println(s == s2);    // false
    }

    // String -> char[]
    @Test
    public void test3() {
        String s1 = "123abc";

        char[] charA = s1.toCharArray();
        for (int i = 0; i < charA.length; i++) {
            System.out.print(charA[i] + " ");   // 1 2 3 a b c
        }

        System.out.println();

        char[] charD = new char[10];
        s1.getChars(1, 4, charD, 3);
        for (int i = 0; i < charD.length; i++) {
            System.out.print(charD[i] + " ");   //       2 3 a        
        }
    }

    // char[] -> String
    @Test
    public void test4() {
        char[] chars = new char[] {'1', '2', '3', 'a', 'b', 'c'};

        String s1 = new String(chars);
        System.out.println(s1);

        String s2 = new String(chars,1, chars.length - 1 - 1);  // 23ab
        System.out.println(s2);
    }

    // String -> byte[]
    @Test
    public void test5() {
        String s1 = "123abc";

        byte[] bytes = s1.getBytes();
        System.out.println(Arrays.toString(bytes)); // [49, 50, 51, 97, 98, 99]

        System.out.println(Arrays.toString("中国".getBytes()));   // [-28, -72, -83, -27, -101, -67] UTF-8
        try {
            System.out.println(Arrays.toString("GBK".getBytes("GBK")));   // [71, 66, 75] GBK
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    // byte[] -> String
    @Test
    public void test6() {
        byte[] bytes = new byte[] {49, 50, 51, 97, 98, 99};

        String s1 = new String(bytes);
        System.out.println(s1); // 123abc

        byte[] bytes1 = new byte[] {-28, -72, -83, -27, -101, -67};
        String s2 = new String(bytes1, StandardCharsets.UTF_8);
        System.out.println(s2);    // 中国

        String s3 = new String(bytes1, 0, 3, StandardCharsets.UTF_8);
        System.out.println(s3);    // 中
    }
}
