package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : switch语句例题1
 * 使用 switch 把小写类型的 char 型转为大写。只转换 a, b, c, d, e，其它的输出 “other”。
 */

public class SwitchTest1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();
        char c = word.charAt(0);

        switch (c) {
            case 'a':
                System.out.println('A');
                break;
            case 'b':
                System.out.println('B');
                break;
            case 'c':
                System.out.println('C');
                break;
            case 'd':
                System.out.println('D');
                break;
            case 'e':
                System.out.println('E');
                break;
            default:
                System.out.println("other");
        }

    }
}
