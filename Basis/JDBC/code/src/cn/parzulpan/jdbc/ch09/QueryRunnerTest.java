package cn.parzulpan.jdbc.ch09;

import cn.parzulpan.jdbc.bean.Customer;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : DBUtils QueryRunner 使用
 */

public class QueryRunnerTest {

    // 插入记录
    @Test
    public void testInsert() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int update = queryRunner.update(connection, sql, "劲儿", "jiner@163.com", "1995-12-29");
            System.out.println("添加了 " + update + " 条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 删除记录
    @Test
    public void testDelete() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "delete from customers where id < ?";
            int update = queryRunner.update(connection, sql, 3);
            System.out.println("删除了 " + update + " 条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 修改记录
    @Test
    public void testUpdate() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "update customers set email = ? where name = ?";
            int update = queryRunner.update(connection, sql, "jinerR@163.com", "劲儿");
            System.out.println("修改了 " + update + " 条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询一条记录，BeanHandler
    @Test
    public void testQuery() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id = ?";
            // BeanHandler 是 ResultSetHandler 的实现类，用于封装表中的一条记录
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = queryRunner.query(connection, sql, handler, 23);
            System.out.println(customer);   // Customer{id=23, name='劲儿', email='jinerR@163.com', birth=1995-12-29}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询一条记录，MapHandler
    @Test
    public void testQueryMap() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id = ?";
            MapHandler mapHandler = new MapHandler();
            Map<String, Object> map = queryRunner.query(connection, sql, mapHandler, 23);
            System.out.println(map);    // {name=劲儿, birth=1995-12-29, id=23, email=jinerR@163.com}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询多条记录，BeanListHandler
    @Test
    public void testQueryList() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id < ?";
            BeanListHandler<Customer> listHandler = new BeanListHandler<>(Customer.class);
            List<Customer> customerList = queryRunner.query(connection, sql, listHandler, 13);
            customerList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询多条记录，MapListHandler
    @Test
    public void testQueryMapList() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id < ?";
            MapListHandler mapListHandler = new MapListHandler();
            List<Map<String, Object>> mapList = queryRunner.query(connection, sql, mapListHandler, 13);
            mapList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询特殊值，类似于最大的，最小的，平均的，总和，个数相关的数据，ScalarHandler
    @Test
    public void testQueryScalar() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();

            String sql = "select count(*) from customers where id < ?";
            ScalarHandler scalarHandler = new ScalarHandler();
            Object query = queryRunner.query(connection, sql, scalarHandler, 20);
            System.out.println(query);

            String sql1 = "select max(birth) from customers";
            ScalarHandler handler = new ScalarHandler();
            Object query1 = queryRunner.query(connection, sql1, handler);
            System.out.println(query1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }


}
