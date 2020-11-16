package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 如何求一个 0~255 范围内的整数的十六进制值，例如 60 的十六进制表示形式 3C ？
 */

public class GetHex {
    public static void main(String[] args) {
        //方式一：自动实现
        String str1 = Integer.toBinaryString(60);
        System.out.println(str1);   // 111100
        String str2 = Integer.toHexString(60);
        System.out.println(str2);   // 3c

        //方式二：手动实现
        int i1 = 60;
        int i2 = i1&15;
        String j = (i2 > 9)? (char)(i2-10 + 'A')+"" : i2+"";
        int temp = i1 >>> 4;
        i2 = temp & 15;
        String k = (i2 > 9)? (char)(i2-10 + 'A')+"" : i2+"";
        System.out.println(k+""+j); //3C
    }
}
