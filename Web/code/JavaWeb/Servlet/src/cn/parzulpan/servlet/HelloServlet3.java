package cn.parzulpan.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-07
 * @Desc : IDEA 创建 Servlet 程序，使用注解
 */

// urlPatterns 相当于 web.xml 的 url-pattern
@WebServlet(name = "HelloServlet3", urlPatterns = ("/hello3"))
public class HelloServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("IDEA 创建 Servlet 程序 doPost()");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("IDEA 创建 Servlet 程序 doGet()");
    }
}
