package cn.parzulpan.jdbc.exer;

import cn.parzulpan.jdbc.util.JDBCUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : 练习1：从控制台向数据库的表 customers 中插入一条数据
 */

public class Test1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter name: ");
        String name = scanner.next();
        System.out.println("enter email: ");
        String email = scanner.next();
        System.out.println("enter birth");
        String date = scanner.next();
        java.sql.Date birth = null;
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            birth = new java.sql.Date(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql = "insert into customers(name, email, birth)values(?,?,?)";

        int update = JDBCUtils.update(sql, name, email, birth);
        if (update > 0) {
            System.out.println("插入成功!");
        } else {
            System.out.println("插入失败!");
        }
    }
}
