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
 * @Desc : 请求转发，Servlet2
 */

@WebServlet(name = "ForwardServlet2", urlPatterns = ("/forwardServlet2"))
public class ForwardServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数（材料）
        String username = request.getParameter("username");
        System.out.println("ForwardServlet2: " + username); // ForwardServlet2: parzulpan

        // 查看 ForwardServlet1
        Object key1 = request.getAttribute("key1");
        System.out.println("ForwardServlet1: " + key1); // ForwardServlet1: ForwardServlet1 doGet()

        // 处理业务
        System.out.println("ForwardServlet2 doGet(): ");    // ForwardServlet2 doGet():
    }
}
