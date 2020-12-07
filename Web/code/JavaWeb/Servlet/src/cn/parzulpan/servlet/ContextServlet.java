package cn.parzulpan.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-07
 * @Desc :
 */

public class ContextServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // * 获取 web.xml 中配置的上下文参数 `context-param`
        ServletContext servletContext = getServletContext();
        String username = servletContext.getInitParameter("username");
        System.out.println("context-param 参数 username 的值是: " + username);
        System.out.println("context-param 参数 password 的值是: " + servletContext.getInitParameter("password"));

        // * 获取当前的工程路径，格式: `/工程路径`
        System.out.println( "当前工程路径: " + servletContext.getContextPath());  // 当前工程路径: /Servlet

        // * 获取工程部署后在服务器磁盘上的绝对路径
        // / 斜杠被服务器解析地址为: http://ip:port/工程名/ 映射到 IDEA 代码的 web 目录
        // 工程部署的路径是: /Users/parzulpan/Study/LearnJava/Web/code/JavaWeb/out/artifacts/Servlet_war_exploded/
        System.out.println("工程部署的路径是: " + servletContext.getRealPath("/"));

        // * 像 Map 一样存取数据
        // 保存之前: Context1 获取 key1 的值是: null
        System.out.println("保存之前: Context1 获取 key1 的值是: "+ servletContext.getAttribute("key1"));
        servletContext.setAttribute("key1", "value1");
        // 更新之后: Context1 中获取 key1 的值是: value1
        System.out.println("更新之后: Context1 中获取 key1 的值是: "+ servletContext.getAttribute("key1"));
    }
}
