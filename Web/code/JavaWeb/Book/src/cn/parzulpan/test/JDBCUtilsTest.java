package cn.parzulpan.test;

import cn.parzulpan.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

public class JDBCUtilsTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Connection connection = JDBCUtils.getConnection();
            System.out.println(connection);
            JDBCUtils.close(connection, null, null);
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            Connection connection = JDBCUtils.getConnection();
            System.out.println(connection);
            JDBCUtils.close(connection, null, null);
        }
    }

}