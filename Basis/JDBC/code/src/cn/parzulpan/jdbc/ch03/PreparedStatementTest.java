package cn.parzulpan.jdbc.ch03;

import cn.parzulpan.jdbc.bean.Customer;
import cn.parzulpan.jdbc.bean.Order;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : PreparedStatement 的使用
 */

public class PreparedStatementTest {

    // 向 customers 表中添加一条记录
    @Test
    public void testInsert() {
        String sql = "insert into customers(name, email, birth)values(?,?,?)";
        String name = "咖喱";
        String email = "curry@gmail.com";
        java.sql.Date date = null;
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("1999-05-05");
            date = new java.sql.Date(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JDBCUtils.update(sql, name, email, date);
    }

    // 从 customers 表中删除一条记录
    @Test
    public void testDelete() {
        String sql = "delete from customers where name = ?";
        String name = "咖喱";
        JDBCUtils.update(sql, name);
    }

    // 向 customers 表中更新一条记录
    @Test
    public void testUpdate() {
        String sql = "update customers set email = ? where name = ?";
        String name = "咖喱";
        String email = "currySuper@gmail.com";
        JDBCUtils.update(sql, email, name);
    }

    // 从 customers 和 order 表中查询一条记录
    @Test
    public void testSelect() {
        String sql = "select id, name, email, birth from customers where name = ?";
        String name = "咖喱";
        JDBCUtils jdbcUtils = new JDBCUtils();
        Customer customer = jdbcUtils.getQuery(Customer.class, sql, name);
        System.out.println(customer);

        System.out.println();

        // 注意别名问题和表名是关键字的问题（``）
        String sql1 = "select order_id orderId, order_name orderName, order_date orderDate from `order` " +
                "where order_id = ?";
        int orderId = 4;
        Order order = jdbcUtils.getQuery(Order.class, sql1, orderId);
        System.out.println(order);
    }

    // 从 customers 和 order 表中查询多条记录
    @Test
    public void testAllSelect() {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql2 = "select id, name, email, birth from customers where id < ?";
        int id = 10;
        List<Customer> customers = jdbcUtils.getAllQuery(Customer.class, sql2, id);
        customers.forEach(System.out::println);

        System.out.println();

        String sql1 = "select order_id orderId, order_name orderName, order_date orderDate from `order` " +
                "where order_id < ?";
        int orderId = 4;
        List<Order> orders = jdbcUtils.getAllQuery(Order.class, sql1, orderId);
        orders.stream().forEach(System.out::println);
    }
}
