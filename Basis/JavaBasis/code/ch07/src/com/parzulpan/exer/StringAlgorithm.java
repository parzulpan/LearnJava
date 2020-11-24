package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 模拟一个 trim 方法，去除字符串两端的空格?
 */

public class StringAlgorithm {
    public static void main(String[] args) {
        System.out.println(StringTrim(" fas kf sf  "));
    }

    public static String StringTrim(String s) {
        int start = 0;
        int end = s.length() - 1;

        while (start < end && s.charAt(start) == ' ') {
            ++start;
        }
        while (start < end && s.charAt(end) == ' ') {
            --end;
        }

        return s.substring(start, end + 1);
    }
}
