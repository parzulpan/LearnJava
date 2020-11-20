package com.parzulpan.java.ch04;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-11-20
 * @Desc : 多态的使用举例二，操作不同的数据库
 */

public class PolymorphismTest1 {
    public static void main(String[] args) {
        DBDriver d = new DBDriver();
//        d.doData(new MySQLConnection);
//        d.doData(new OracleConnection);
//        d.doData(new Db2Connection);
    }
}


class DBDriver {
    public void doData(Connection connection) {
        // 一些数据库操作
    }
}