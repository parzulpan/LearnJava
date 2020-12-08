package cn.parzulpan.service;

import cn.parzulpan.bean.User;
import cn.parzulpan.dao.UserDAO;
import cn.parzulpan.dao.UserDAOImpl;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

public interface UserService {
    UserDAO userDAO =  new UserDAOImpl();

    /**
     * 用户注册
     * @param user 用户
     * @return 返回 -1 代表注册失败；否则注册成功
     */
    public int regist(User user);

    /**
     * 用户登录
     * @param user 用户
     * @return 返回 null 代表登录失败；否则登录成功
     */
    public User login(User user);

    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @return 返回 true 表示用户名已存在；返回 false 表示用户名可用
     */
    public boolean checkUsername(String username);

}
