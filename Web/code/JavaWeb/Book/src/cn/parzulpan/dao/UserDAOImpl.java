package cn.parzulpan.dao;

import cn.parzulpan.bean.User;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    /**
     * 根据用户名查询用户信息
     *
     * @param connection 数据库连接
     * @param username   用户名
     * @return 如果返回 null，说明没有这个用户。反之亦然
     */
    @Override
    public User queryUserByUsername(Connection connection, String username) {
        String sql = "select `id`, `username`, `password`, `email` from t_user where username = ?";
        return getBean(connection, sql, username);
    }

    /**
     * 根据用户名和密码查询用户信息
     *
     * @param connection 数据库连接
     * @param username   用户名
     * @param password   密码
     * @return 如果返回 null，说明用户名或密码错误。反之亦然
     */
    @Override
    public User queryUserByUsernameAndPassword(Connection connection, String username, String password) {
        String sql = "select `id`, `username`, `password`, `email` from t_user where username = ? and password = ?";
        return getBean(connection, sql, username, password);
    }

    /**
     * 保存用户信息
     *
     * @param connection 数据库连接
     * @param user       User Bean
     * @return 返回 -1 表示操作失败；否则返回 sql 语句影响的行数
     */
    @Override
    public int saveUser(Connection connection, User user) {
        String sql = "insert into t_user(`username`, `password`, `email`) values (?, ? ,?)";
        return update(connection, sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}
