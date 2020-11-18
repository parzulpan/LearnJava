package com.parzulpan.java.ch03;

import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-17
 * @Desc : 从键盘读入学生成绩，找出最高分，并输出学生成绩等级。
 * 成绩>=最高分-10 等级为’A’
 * 成绩>=最高分-20 等级为’B’
 * 成绩>=最高分-30 等级为’C’
 * 其余 等级为’D’
 */

public class ArrayTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入学生人数：");
        int studentNumber = scanner.nextInt();
        int[] studentScores = new int[studentNumber];
        int maxScore = 0;
        int temp;
        System.out.println("请输入 " + studentNumber + " 个成绩：");
        for (int i = 0; i < studentScores.length; ++i) {
            temp = scanner.nextInt();
            studentScores[i] = temp;
            if (maxScore < temp) {
                maxScore = temp;
            }
        }

        System.out.println("最高分是：" + maxScore);

        char level;
        for (int i = 0; i < studentScores.length; ++i) {
            if (maxScore - 10 <= studentScores[i]) {
                level = 'A';
            } else if (maxScore - 20 <= studentScores[i]) {
                level = 'B';
            } else if (maxScore - 30 <= studentScores[i]) {
                level = 'C';
            } else {
                level = 'D';
            }
            System.out.println("student " + i + " , score is " + studentScores[i] + " , grade is " + level);
        }

    }
}
