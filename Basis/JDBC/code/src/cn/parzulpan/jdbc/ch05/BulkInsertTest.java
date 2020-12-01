package cn.parzulpan.jdbc.ch05;

import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 批量插入
 */

public class BulkInsertTest {

    // 实现方式一：使用 Statement
    @Test
    public void test1() throws Exception {
        long start = System.currentTimeMillis();

        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        for (int i = 0; i < 1000; i++) {
            String sql = "insert into goods(name)values('name_" + i + "')";
            statement.executeUpdate(sql);
        }

        JDBCUtils.closeResource(connection, statement);

        long end = System.currentTimeMillis();
        System.out.println(end - start);    // 1000 -> 131751ms
    }

    // 实现方式二：使用 PrepareStatement
    @Test
    public void test2() throws Exception {
        long start = System.currentTimeMillis();

        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into goods(name)values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < 1000; i++) {
            statement.setString(1, "name_" + i);
            statement.executeUpdate();
        }

        JDBCUtils.closeResource(connection, statement);

        long end = System.currentTimeMillis();
        System.out.println(end - start);    // 1000 -> 122340ms
    }

    // 实现方式三：使用 批量处理语句
    // mysql 服务器默认是关闭批处理的，需要通过一个参数，让 mysql 开启批处理的支持。
    // ?rewriteBatchedStatements=true 写在配置文件的 url 后面
    @Test
    public void test3() throws Exception {
        long start = System.currentTimeMillis();

        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into goods(name)values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < 1000; i++) {
            statement.setString(1, "name_" + i);

            // 1. "攒" sql
            statement.addBatch();
            if (i % 500 == 0) {
                // 2. 执行
                statement.executeBatch();
                // 3. 清空
                statement.clearBatch();
            }
        }

        JDBCUtils.closeResource(connection, statement);

        long end = System.currentTimeMillis();
        System.out.println(end - start);    // 1000 -> 69084ms
    }

    // 实现方式四：设置为不自动提交数据
    @Test
    public void test4() throws Exception {
        long start = System.currentTimeMillis();

        Connection connection = JDBCUtils.getConnection();

        // 1. 设置为不自动提交数据
        connection.setAutoCommit(false);
        String sql = "insert into goods(name)values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < 1000; i++) {
            statement.setString(1, "name_" + i);

            // 1. "攒" sql
            statement.addBatch();
            if (i % 500 == 0) {
                // 2. 执行
                statement.executeBatch();
                // 3. 清空
                statement.clearBatch();
            }
        }

        // 2. 提交数据
        connection.commit();

        JDBCUtils.closeResource(connection, statement);

        long end = System.currentTimeMillis();
        System.out.println(end - start);    // 1000 -> 1338ms

    }
}
