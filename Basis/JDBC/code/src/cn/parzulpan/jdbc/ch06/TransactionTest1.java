package cn.parzulpan.jdbc.ch06;

import cn.parzulpan.jdbc.bean.UserTable;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 事务隔离级别
 */

public class TransactionTest1 {

    // 模拟 事务隔离级别
    @Test
    public void test1() throws Exception{
        Connection connection = JDBCUtils.getConnection();

        // TRANSACTION_READ_UNCOMMITTED 读未提交数据
        // 允许事务读取未被其他事务提交的变更，出现 脏读、不可重复读、幻读等问题
        // 先运行 test2()，然后运行 test1()，发现数据已更改，但是10秒后，再然后运行 test1()，发现数据已恢复
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        // TRANSACTION_READ_COMMITTED 读已提交数据
        // 只允许事务读取已经被其他事务提交的变更，避免 脏读问题，出现 不可重复读、幻读等问题
        // 先运行 test2()，然后运行 test1()，发现数据未更改，10秒后，再然后运行 test1()，发现数据依然未更改
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        // TRANSACTION_REPEATABLE_READ 可重复读
        // 确保在同一个事务中多次读取同样记录的结果是一致的，避免 脏读、不可重复读问题，出现 幻读等问题
        // 打开 test2() 自动事务提交，先运行 test2()，然后运行 test1()，发现数据未更改，10秒后，再然后运行 test1()，发现数据依然未更改
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);


        System.out.println(connection.getTransactionIsolation());

        connection.setAutoCommit(false);

        String sql = "select user, password, balance from user_table where user = ?";
        JDBCUtils jdbcUtils = new JDBCUtils();
        UserTable userTable = jdbcUtils.getQuery(connection, UserTable.class, sql, "CC");
        System.out.println(userTable);
    }

    // 模拟 事务隔离级别
    @Test
    public void test2() throws Exception{
        Connection connection = JDBCUtils.getConnection();

        System.out.println(connection.getTransactionIsolation());

        connection.setAutoCommit(false);

        String sql = "update user_table set balance = ? where user = ?";
        JDBCUtils jdbcUtils = new JDBCUtils();
        jdbcUtils.update(connection, sql, 5000, "CC");

        Thread.sleep(5000);
        System.out.println("修改结束"); // 注意此时事务并未提交
    }
}
