package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : 用来方便地实现键盘访问
 */

public class FamilyUtil {
    private static Scanner scanner = new Scanner(System.in);

    // 该方法读取键盘，如果用户键入’1’-’4’中的任意字符，则方法返回。返回值为用户键入字符。
    public static char readMenuSelection() {
        char c;
        while (true) {
            String str = readKeyBoard(1);
            c = str.charAt(0);
            if (c != '1' && c != '2' && c != '3' && c !='4') {
                System.out.println("选择错误！请重新输入：");
            } else {
                break;
            }
        }

        return c;
    }

    // 用于收入和支出金额的输入。该方法从键盘读取一个不超过 4 位长度的整数，并将其作为方法的返回值。
    public static int readNumber() {
        int n;
        while (true) {
            String str = readKeyBoard(4);
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.println("数字输入错误！请重新输入：");
            }
        }

        return n;
    }

    // 用于收入和支出说明的输入。该方法从键盘读取一个不超过 8 位长度的字符串，并将其作为方法的返回值。
    public static String readString() {
        return readKeyBoard(8);
    }

    // 用于确认选择的输入。该方法从键盘读取 'Y' 或者 'N'，并将其作为方法的返回值。
    public static char readConfirmSelection() {
        char c;
        while (true) {
            String str = readKeyBoard(1).toUpperCase();
            c = str.charAt(0);

            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.println("输入错误！请重新输入：");
            }
        }

        return c;
    }

    //
    public static String readKeyBoard(int limit) {
        String line = "";

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if (line.length() < 1 || line.length() > limit) {
                System.out.println("输出长度（不大于 " + limit + "）错误！请重新输入：");
                continue;
            }
            break;
        }

        return line;
    }
}
