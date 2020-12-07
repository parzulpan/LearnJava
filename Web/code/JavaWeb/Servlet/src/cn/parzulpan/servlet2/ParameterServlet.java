package cn.parzulpan.servlet2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Author : parzulpan
 * @Time : 2020-12-07
 * @Desc :
 */

@WebServlet(name = "ParameterServlet", urlPatterns = ("/parameterServlet"))
public class ParameterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
        // Post 请求中文乱码解决
        // 1. 设置请求体的字符集为 UTF-8
        request.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));


        // 获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] hobbies = request.getParameterValues("hobby");

        System.out.println("用户名: " + username);
        System.out.println("密 码: " + password);
        System.out.println("兴趣爱好: " + Arrays.asList(hobbies));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        // Get 请求中文乱码解决
        // 1. 先以 iso8859-1 进行编码
        // 2. 再以 utf-8 进行解码

        // 获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] hobbies = request.getParameterValues("hobby");

        System.out.println("用户名: " + new String(username.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        System.out.println("密 码: " + password);
        System.out.println("兴趣爱好: " + Arrays.asList(hobbies));
    }
}
