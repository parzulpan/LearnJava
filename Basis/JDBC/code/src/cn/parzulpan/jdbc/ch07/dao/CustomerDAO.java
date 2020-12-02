package cn.parzulpan.jdbc.ch07.dao;

import cn.parzulpan.jdbc.ch07.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 此接口用于规范 customers 表的常用操作
 */

public interface CustomerDAO {

    /**
     * 将 customer 对象添加到数据库中
     * @param connection 数据库连接
     * @param customer customer 对象
     */
    void insert(Connection connection, Customer customer);

    /**
     * 用 customer 对象修改数据库对应的数据中
     * @param connection 数据库连接
     * @param customer customer 对象
     */
    void update(Connection connection, Customer customer);

    /**
     * 根据 Id 删除一条记录
     * @param connection 数据库连接
     * @param id
     */
    void deleteById(Connection connection, int id);

    /**
     * 根据 Id 查询一条记录
     * @param connection 数据库连接
     * @param id
     * @return
     */
    Customer getCustomerById(Connection connection, int id);

    /**
     * 查询多条记录
     * @param connection 数据库连接
     * @return
     */
    List<Customer> getCustomerList(Connection connection);

    /**
     * 查询记录条数
     * @param connection 数据库连接
     * @return
     */
    Long getCount(Connection connection);

    /**
     * 查询最大的生日记录
     * @param connection 数据库连接
     * @return
     */
    Date getMaxBirth(Connection connection);

}
