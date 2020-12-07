package cn.parzulpan.servlet2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-07
 * @Desc :
 */

@WebServlet(name = "RequestAPIServlet", urlPatterns = ("/requestAPI"))
public class RequestAPIServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // * `getRequestURI()` 获取请求的资源路径
        System.out.println("URI: " + req.getRequestURI());  // /Servlet/requestAPI

        // * `getRequestURL()` 获取请求的统一资源定位符（绝对路径）
        System.out.println("URL: " + req.getRequestURL());  // http://localhost:8080/Servlet/requestAPI

        // * `getRemoteHost()` 获取客户端的 ip 地址
        System.out.println("客户端的 ip 地址: " + req.getRemoteHost());   // 127.0.0.1

        // * `getHeader()` 获取请求头
        System.out.println("请求头User-Agent: " + req.getHeader("User-Agent"));    //

        // * `getParameter()` 获取请求的参数
        // * `getParameterValues()` 获取请求的参数（多个值的时候使用）
        // * `getMethod()` 获取请求的方式 GET 或 POST
        System.out.println("请求的方式: " + req.getMethod());    // GET

        // * `setAttribute(key, value)` 设置域数据
        // * `getAttribute(key)` 获取域数据
        // * `getRequestDispatcher()` 获取请求转发对象
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
