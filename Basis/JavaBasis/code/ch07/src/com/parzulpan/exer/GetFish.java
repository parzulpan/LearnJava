package com.parzulpan.exer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : 三天打鱼，两天晒网
 */

public class GetFish {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入年：");
            int year = scanner.nextInt();
            System.out.print("请输入月：");
            int month = scanner.nextInt();
            System.out.print("请输入日：");
            int day = scanner.nextInt();
            long dayDistance = getDayDistance("1990/1/1", year + "/" + month + "/" + day);
            if (dayDistance % 5 == 0 || dayDistance % 5 == 4) {
                System.out.println("晒网");
            } else {
                System.out.println("打鱼");
            }
        }

    }

    public static long getDayDistance(String time1, String time2) {
        long dayDistance = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            dayDistance = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayDistance;
    }
}
