package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : if语句例题1
 * 岳小鹏参加Java考试，他和父亲岳不群达成承诺：
 * 如果：
 * 成绩为100分时，奖励一辆BMW；
 * 成绩为(80，99]时，奖励一台iphone xs max；
 * 当成绩为[60,80]时，奖励一个 iPad；
 * 其它时，什么奖励也没有。
 * 请从键盘输入岳小鹏的期末成绩，并加以判断。
 */

public class IfTest1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请从键盘输入岳小鹏的期末成绩：");
        int score = scanner.nextInt();  // 从键盘输入一个整数
        if(score == 100) {
            System.out.println("奖励一辆BMW");
        } else if(score <= 90 && score > 80) {
            System.out.println("奖励一台iphone xs max");
        } else if(score <= 80 && score >= 60) {
            System.out.println("奖励一个 iPad");
        } else {
            System.out.println("什么奖励也没有");
        }
    }

}
