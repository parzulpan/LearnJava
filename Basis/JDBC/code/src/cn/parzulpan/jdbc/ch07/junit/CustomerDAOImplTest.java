package cn.parzulpan.jdbc.ch07.junit;

import cn.parzulpan.jdbc.ch07.bean.Customer;
import cn.parzulpan.jdbc.ch07.impl.CustomerDAOImpl;
import cn.parzulpan.jdbc.ch07.util.JDBCUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author : parzulpan
 * @Time : 2020-12-02
 * @Desc :
 */

public class CustomerDAOImplTest {


    @Before
    public void setUp() throws Exception {
        System.out.println("CustomerDAOImplTest Before");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("CustomerDAOImplTest Down");
    }

    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();

    @Test
    public void insert() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            Customer customer = new Customer(30, "DAO", "dao@163.com", JDBCUtils.getSqlDate("1995-1-1"));
            customerDAO.insert(connection, customer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }

    @Test
    public void update() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            Customer customer = new Customer(24, "DAOUpdate", "dao@163.com", JDBCUtils.getSqlDate("1995-1-1"));
            customerDAO.update(connection, customer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }

    @Test
    public void deleteById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            customerDAO.deleteById(connection, 23);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }

    @Test
    public void getCustomerById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            Customer customer = customerDAO.getCustomerById(connection, 10);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }

    @Test
    public void getCustomerList() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            List<Customer> customers = customerDAO.getCustomerList(connection);
            customers.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }

    @Test
    public void getCount() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            Long count = customerDAO.getCount(connection);
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }

    @Test
    public void getMaxBirth() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            Date maxBirth = customerDAO.getMaxBirth(connection);
            System.out.println(maxBirth);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourceQuietly(connection, null, null);
        }
    }
}