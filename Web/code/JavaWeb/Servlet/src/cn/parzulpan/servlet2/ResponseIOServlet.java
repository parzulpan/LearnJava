package cn.parzulpan.servlet2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc :
 */

@WebServlet(name = "ResponseIOServlet", urlPatterns = ("/responseIOServlet"))
public class ResponseIOServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决乱码问题
        // 方法1，不推荐使用
        // 1. 设置服务器字符集
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");

        // 方法2，推荐使用
        response.setContentType("text/html; charset=UTF-8");

        // 往客户端回传字符串数据
        PrintWriter writer = response.getWriter();
        writer.write("response content! 往客户端回传字符串数据");
    }
}
