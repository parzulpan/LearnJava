package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time :2020-11-16
 * @attention : 为抵抗洪水，战士连续作战 89 小时，编程计算共多少天零多少小时？
 */

public class GetDay {
    public static void main(String[] args) {
        int all = 89;
        int day = all / 24;
        int h = all % 24;
        System.out.println("共 " + day + " 天零 " + h + " 小时");
    }
}