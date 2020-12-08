package cn.parzulpan.servlet2;

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

@WebServlet(name = "ResponseServlet1", urlPatterns = ("/responseServlet1"))
public class ResponseServlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("see responseServlet1");

        request.setAttribute("key1", "value1");

        // 请求重定向的第一种方案
        // 1. 设置响应状态码 302 ，表示重定向
//        response.setStatus(302);
        // 2. 设置响应头，说明新的地址在哪里
//        response.setHeader("Location", "http://localhost:8080/Servlet/responseServlet2");

        // 请求重定向的第二种方案，推荐使用
        response.sendRedirect("http://localhost:8080/Servlet/responseServlet2");
    }
}
