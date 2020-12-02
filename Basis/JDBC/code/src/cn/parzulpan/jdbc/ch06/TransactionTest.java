package cn.parzulpan.jdbc.ch06;

import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 事务问题，AA 用户 向 BB 用户转账 100
 */

public class TransactionTest {

    // 出现事务问题
    @Test
    public void test1() {
        String sql1 = "update user_table set balance = balance - 100 where user = ?";
        JDBCUtils.update(sql1, "AA");

        // 模拟网络异常
        System.out.println(10 / 0);

        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        JDBCUtils.update(sql2, "BB");
    }

    // 考虑事务问题
    @Test
    public void test2() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            // 取消事务自动提交
            connection.setAutoCommit(false);

            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            JDBCUtils.update(connection, sql1, "AA");

            // 模拟网络异常
            System.out.println(10 / 0);

            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            JDBCUtils.update(connection, sql2, "BB");

            // 若没有异常，则提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // 如有异常，则回滚事务
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                // 恢复每次 DML 操作的自动提交功能，针对于数据库连接池时
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 关闭连接
            JDBCUtils.closeResource(connection, null, null);
        }
    }
}
