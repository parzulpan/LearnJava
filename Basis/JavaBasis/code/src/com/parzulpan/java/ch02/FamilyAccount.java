package com.parzulpan.java.ch02;

/**
 * @author : parzulpan
 * @time : 2020-11-16
 * @attention : Java 基础项目一：家庭收支记账软件
 */

public class FamilyAccount {
    public static void main(String[] args) {
        String details = "收支\t账户金额\t收支金额\t说  明\n";
        int balance = 10000;
        boolean isFlag = true;

        do {
            System.out.println("\n-------------家庭收支记账软件-------------\n");
            System.out.println("               1 收支明细                    ");
            System.out.println("               2 登记收入                    ");
            System.out.println("               3 登记支出                    ");
            System.out.println("               4 退    出                   ");
            System.out.print("               请选择（1-4）：");

            char menu = FamilyUtil.readMenuSelection();
            switch (menu) {
                case '1':
                    System.out.println("-------------当前收支明细记录-------------");
                    System.out.println(details);
                    System.out.println("----------------------------------------");
                    break;
                case '2':
                    System.out.print("本次收入金额：");
                    int amount = FamilyUtil.readNumber();
                    System.out.print("本次收入说明：");
                    String desc = FamilyUtil.readString();
                    balance += amount;
                    details += "收入\t" + balance + "\t\t" + amount + "\t\t" + desc + "\n";
                    System.out.println("---------------收入登记完成---------------");
                    break;
                case '3':
                    System.out.print("本次支出金额：");
                    int amount2 = FamilyUtil.readNumber();
                    System.out.print("本次支出说明：");
                    String desc2 = FamilyUtil.readString();
                    balance -= amount2;
                    details += "支出\t" + balance + "\t\t" + amount2 + "\t\t" + desc2 + "\n";
                    System.out.println("---------------支出登记完成---------------");
                    break;
                case '4':
                    System.out.print("确定退出吗？（Y/N）");
                    char exit = FamilyUtil.readConfirmSelection();
                    if (exit == 'Y') {
                        isFlag = false;
                    }
                    break;
            }
        } while (isFlag);
    }
}
