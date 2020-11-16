package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 今天是周二，100 天以后是周几？
 */

public class GetWeek {
    public static void main(String[] args) {
        int all = 100 + 2;
        int week = all % 7;
        System.out.println("今天是周二，100 天以后是周 " + week);
    }
}
