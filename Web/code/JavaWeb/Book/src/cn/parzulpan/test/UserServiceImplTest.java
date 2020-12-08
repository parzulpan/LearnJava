package cn.parzulpan.test;

import cn.parzulpan.bean.User;
import cn.parzulpan.service.UserService;
import cn.parzulpan.service.UserServiceImpl;
import org.junit.Test;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

public class UserServiceImplTest {
    UserService userService = new UserServiceImpl();


    @Test
    public void regist() {
        int regist = userService.regist(new User(null, "admin11", "admin11", "admin11@163.com"));
        if (regist == -1) {
            System.out.println("注册失败！");
        } else {
            System.out.println("注册成功！");
        }
    }

    @Test
    public void login() {
        User login = userService.login(new User(null, "admin", "admin", null));
        if (login == null) {
            System.out.println("登录失败！");
        } else {
            System.out.println("登录成功！");
        }
    }

    @Test
    public void checkUsername() {
        if (userService.checkUsername("admin")) {
            System.out.println("用户名已存在！");
        } else {
            System.out.println("用户名可用！");
        }
    }
}