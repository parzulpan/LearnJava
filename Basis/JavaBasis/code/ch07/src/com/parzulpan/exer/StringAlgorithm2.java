package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 获取一个字符串在另一个字符串中出现的次数。比如：获取 “ab” 在 “abkkcadkabkebfkabkskab” 中出现的次数？
 */

public class StringAlgorithm2 {
    public static void main(String[] args) {
        System.out.println(getCount("abkkcadkabkebfkabkskab", "ab"));
    }

    public static int getCount(String mainStr, String subStr) {
        if (mainStr.length() >= subStr.length()) {
            int count = 0;
            int index = 0;

            while ((index = mainStr.indexOf(subStr, index)) != -1) {
                count++;
                index += subStr.length();
            }

            return count;
        } else {
            return 0;
        }

    }
}
