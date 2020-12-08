package cn.parzulpan.service;

import cn.parzulpan.bean.User;
import cn.parzulpan.dao.UserDAO;
import cn.parzulpan.dao.UserDAOImpl;
import cn.parzulpan.utils.JDBCUtils;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

public class UserServiceImpl implements UserService {
    UserDAO userDAO =  new UserDAOImpl();

    /**
     * 用户注册
     *
     * @param user 用户
     * @return 返回 -1 代表注册失败；否则注册成功
     */
    @Override
    public int regist(User user) {
        Connection connection = JDBCUtils.getConnection();
        int saveUser = userDAO.saveUser(connection, new User(null, user.getUsername(),
                user.getPassword(), user.getEmail()));
        JDBCUtils.close(connection, null, null);
        return saveUser;
    }

    /**
     * 用户登录
     *
     * @param user 用户
     * @return 返回 null 代表登录失败；否则登录成功
     */
    @Override
    public User login(User user) {
        Connection connection = JDBCUtils.getConnection();
        User user1 = userDAO.queryUserByUsernameAndPassword(connection, user.getUsername(), user.getPassword());
        JDBCUtils.close(connection, null, null);
        return user1;
    }

    /**
     * 检查用户名是否可用
     *
     * @param username 用户名
     * @return 返回 true 表示用户名已存在；返回 false 表示用户名可用
     */
    @Override
    public boolean checkUsername(String username) {
        Connection connection = JDBCUtils.getConnection();
        User user = userDAO.queryUserByUsername(connection, username);
        JDBCUtils.close(connection, null, null);
        return user != null;
    }
}
