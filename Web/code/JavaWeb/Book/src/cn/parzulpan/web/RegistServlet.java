package cn.parzulpan.web;

import cn.parzulpan.bean.User;
import cn.parzulpan.service.UserService;
import cn.parzulpan.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

@WebServlet(name = "RegistServlet", urlPatterns = ("/registServlet"))
public class RegistServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        // 由于目前还没有使用服务器生成验证码，写死要求验证码为 bnbnp
        if ("bnbnp".equalsIgnoreCase(code)) {
            // 检查用户名是否可用
            if (userService.checkUsername(username)) {
                // 打印信息
                System.out.println("用户名 " + username + " 已存在！");
                // 跳回注册页面
                request.getRequestDispatcher("/pages/user/regist.html").forward(request, response);
            } else {
                // 保存到数据库
                userService.regist(new User(null, username, password, email));
                // 跳到注册成功页面
                request.getRequestDispatcher("/pages/user/regist_success.html").forward(request, response);
            }
        } else {
            // 打印信息
            System.out.println("验证码 " + code + " 错误！");
            // 跳回注册页面
            request.getRequestDispatcher("/pages/user/regist.html").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
