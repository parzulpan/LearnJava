package cn.parzulpan.test;

import cn.parzulpan.bean.User;
import cn.parzulpan.dao.UserDAO;
import cn.parzulpan.dao.UserDAOImpl;
import cn.parzulpan.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;


/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

public class UserDAOImplTest {
    UserDAO userDAO =  new UserDAOImpl();

    @Test
    public void queryUserByUsername() {
        Connection connection = JDBCUtils.getConnection();
        if (userDAO.queryUserByUsername(connection, "admin") == null) {
            System.out.println("用户名可用！");
        } else {
            System.out.println("用户名已存在！");
        }
        JDBCUtils.close(connection, null, null);
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        Connection connection = JDBCUtils.getConnection();
        if (userDAO.queryUserByUsernameAndPassword(connection, "admin", "admin1") == null) {
            System.out.println("用户名或密码错误！");
        } else {
            System.out.println("查询成功！");
        }
        JDBCUtils.close(connection, null, null);
    }

    @Test
    public void saveUser() {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(userDAO.saveUser(connection, new User(null, "parzulpan123",
                "pp123", "parzulpan123@gmail.com")));
        JDBCUtils.close(connection, null, null);
    }
}