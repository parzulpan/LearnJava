package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 将一个字符串进行反转。将字符串中指定部分进行反转。比如 “abcdefg” 反转为 ”abfedcg”？
 */

public class StringAlgorithm1 {
    public static void main(String[] args) {
        System.out.println(subReverse("abcdefg", 2, 5));

    }

    public static String subReverse(String s, int start, int end) {
        char[] chars = s.toCharArray();
        for (int i = start, j = end; i < j;i++, j--) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }
}
