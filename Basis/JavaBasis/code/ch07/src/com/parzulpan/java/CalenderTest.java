package com.parzulpan.java;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author : parzulpan
 * @Time : 2020-11-24
 * @Desc : java.util.Calendar(日历)类
 */

public class CalenderTest {

    @Test
    public void test1() {
        // 实例化
        // 方式一
        Calendar calendar = Calendar.getInstance();
        // 方式二
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        // get()
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(dayOfMonth); // 24

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear);  // 329

        // set()
        calendar.set(Calendar.DAY_OF_YEAR, 330);
        int dayOfYear1 = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear1); //330

        // add()
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int dayOfYear2 = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear2); //331

        // getTime()
        Date date = calendar.getTime();
        System.out.println(date);   // Thu Nov 26 17:45:30 CST 2020

        // setTime()
        Date date1 = new Date();
        calendar.setTime(date1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);  // 24

    }
}
