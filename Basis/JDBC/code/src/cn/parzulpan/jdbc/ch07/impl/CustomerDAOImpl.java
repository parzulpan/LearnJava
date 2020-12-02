package cn.parzulpan.jdbc.ch07.impl;

import cn.parzulpan.jdbc.ch07.bean.Customer;
import cn.parzulpan.jdbc.ch07.dao.BaseDAO;
import cn.parzulpan.jdbc.ch07.dao.CustomerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc :
 */

public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {
    /**
     * 将 customer 对象添加到数据库中
     *
     * @param connection 数据库连接
     * @param customer   customer 对象
     */
    @Override
    public void insert(Connection connection, Customer customer) {
        String sql = "insert into customers(name, email, birth)values(?,?,?)";
        update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    /**
     * 用 customer 对象修改数据库对应的数据中
     *
     * @param connection 数据库连接
     * @param customer   customer 对象
     */
    @Override
    public void update(Connection connection, Customer customer) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";
        update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    /**
     * 根据 Id 删除一条记录
     *
     * @param connection 数据库连接
     * @param id
     */
    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from customers where id = ?";
        update(connection, sql, id);
    }

    /**
     * 根据 Id 查询一条记录
     *
     * @param connection 数据库连接
     * @param id
     * @return
     */
    public Customer getCustomerById(Connection connection,  int id) {
        String sql = "select id, name, email, birth from customers where id = ?";
        return getBean(connection, sql, id);
    }

    /**
     * 查询多条记录
     *
     * @param connection 数据库连接
     * @return
     */
    @Override
    public List<Customer> getCustomerList(Connection connection) {
        String sql = "select id, name, email, birth from customers";
        return getBeanList(connection, sql);
    }

    /**
     * 查询记录条数
     *
     * @param connection 数据库连接
     * @return
     */
    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from customers";
        return (Long) getValue(connection, sql);
    }

    /**
     * 查询最大的生日记录
     *
     * @param connection 数据库连接
     * @return
     */
    @Override
    public Date getMaxBirth(Connection connection) {
        String sql = "select max(birth) from customers";
        return (Date) getValue(connection, sql);
    }
}
