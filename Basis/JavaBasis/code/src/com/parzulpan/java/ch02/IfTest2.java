package com.parzulpan.java.ch02;

import java.util.Scanner;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : if语句例题2
 * 假设你想开发一个玩彩票的游戏，程序随机地产生一个两位数的彩票，提示用户输入
 * 一个两位数，然后按照下面的规则判定用户是否能赢。
 * 1)如果用户输入的数匹配彩票的实际顺序，奖金10 000美元。
 * 2)如果用户输入的所有数字匹配彩票的所有数字，但顺序不一致，奖金 3 000美元。
 * 3)如果用户输入的一个数字仅满足顺序情况下匹配彩票的一个数字，奖金1 000美元。
 * 4)如果用户输入的一个数字仅满足非顺序情况下匹配彩票的一个数字，奖金500美元。
 * 5)如果用户输入的数字没有匹配任何一个数字，则彩票作废。
 */

public class IfTest2 {
    public static void main(String[] args) {
        int randomNumber = (int)(Math.random() * 90 + 10);  //  [0.0, 1.0) * 90 -> [0, 90) + 10 -> [10, 100) -> [10, 99]
        System.out.print("请输入一个两位数：");
        Scanner scanner = new Scanner(System.in);
        int inputNumber = scanner.nextInt();

        int randomNumberA = randomNumber / 10;
        int randomNumberB = randomNumber % 10;
        int inputNumberA = inputNumber / 10;
        int inputNumberB = inputNumber % 10;

        if (randomNumber == inputNumber) {
            System.out.println("奖金 10 000 美元");
        } else if (randomNumberA == inputNumberB && randomNumberB == inputNumberA) {
            System.out.println("奖金 3 000 美元");
        } else if (randomNumberA == inputNumberA || randomNumberB == inputNumberB) {
            System.out.println("奖金 1 000 美元");
        } else if (randomNumberA == inputNumberB || randomNumberB == inputNumberA) {
            System.out.println("奖金 500 美元");
        } else {
            System.out.println("没中奖");
        }

        System.out.println("中奖号码是：" + randomNumber);

    }
}
