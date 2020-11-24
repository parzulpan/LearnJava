package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 获取两个字符串中最大相同子串。比如：str1 = "abcwerthelloyuiodef“; str2 = "cvhellobnm"; ？
 */

public class StringAlgorithm3 {
    public static void main(String[] args) {
        System.out.println(getMaxSameSubStr("abcwerthelloyuiodef", "rthesfalloy"));
    }

    public static String getMaxSameSubStr(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) {
            return null;
        }

        String maxStr = (s1.length() > s2.length()) ? s1 : s2;
        String minStr = (s1.length() > s2.length()) ? s2 : s1;

        int len = minStr.length();
        for (int i = 0; i < len; i++) {
            for (int x = 0, y = len - i; y < len; x++, y++) {
                if (maxStr.contains(minStr.substring(x, y + 1))) {
                    return minStr.substring(x, y + 1);
                }
            }
        }

        return null;
    }
}
