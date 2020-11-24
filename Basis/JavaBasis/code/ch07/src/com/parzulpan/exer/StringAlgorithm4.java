package com.parzulpan.exer;

import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 对字符串中字符进行自然顺序排序？
 */

public class StringAlgorithm4 {
    public static void main(String[] args) {
        System.out.println(sortStr("5432shtesdrhJGFIEj"));
    }

    public static String sortStr(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
