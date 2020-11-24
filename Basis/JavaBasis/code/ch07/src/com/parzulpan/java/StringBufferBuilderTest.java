package com.parzulpan.java;

import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc :
 */

public class StringBufferBuilderTest {
    public static void main(String[] args) {
        long startTime = 0L;
        long endTime = 0L;
        String text = "";
        StringBuffer buffer = new StringBuffer("");
        StringBuilder builder = new StringBuilder("");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            buffer.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer的执行时间：" + (endTime - startTime));   // 17

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            builder.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder的执行时间：" + (endTime - startTime));  // 9

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            text = text + i;
        }
        endTime = System.currentTimeMillis();
        System.out.println("String的执行时间：" + (endTime - startTime)); // 1873

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test1() {

        StringBuffer sb1 = new StringBuffer();  // char[] value = new char[16]  底层创建了一个长度为 16 的字符数组
        System.out.println(sb1.length());   // 0
        sb1.append('a');    // value[0] = 'a'
        sb1.append('b');    // value[1] = 'b'
        System.out.println(sb1.length());   // 2

        StringBuilder sb2 = new StringBuilder("abc");    // char[] value = new char["abc".length() + 16]
        System.out.println(sb2.length());   // 3
    }

    @Test
    public void test2() {
        String str = null;

        StringBuffer sb = new StringBuffer();
        sb.append(str);
        System.out.println(sb.length());    // 4
        System.out.println(sb);     // "null"

        StringBuffer sb1 = new StringBuffer(str);
        System.out.println(sb1);    //  NullPointerException
    }
}
