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

@WebServlet(name = "LoginServlet", urlPatterns = ("/loginServlet"))
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.login(new User(null, username, password, null));
        if (user == null) {
            // 登录失败
            System.out.println("登录失败！");
            request.getRequestDispatcher("/pages/user/login.html").forward(request, response);
        } else {
            System.out.println("登录成功！");
            request.getRequestDispatcher("/pages/user/login_success.html").forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
