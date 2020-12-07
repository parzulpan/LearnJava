package cn.parzulpan.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-06
 * @Desc :
 */

public class HelloServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // * 获取 Servlet 程序的别名 servlet-name 的值
        System.out.println("Servlet 程序的别名" + servletConfig.getServletName());

        // * 获取初始化参数 init-param
        System.out.println("获取初始化参数 username 的值：" + servletConfig.getInitParameter("username"));
        System.out.println("获取初始化参数 url 的值：" + servletConfig.getInitParameter("url"));

        // * 获取 ServletContext 对象
        System.out.println(servletConfig.getServletContext());
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 专门处理请求和响应
     * @param servletRequest 包含客户端请求的 ServletRequest 对象
     * @param servletResponse 包含 Servlet 响应的 ServletResponse 对象
     * @throws ServletException .
     * @throws IOException .
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Hello Servlet 被访问了！");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 获取请求的方式
        String method = httpServletRequest.getMethod();

        if ("GET".equals(method)) {
            doGet();
        } else if ("POST".equals(method)) {
            doPost();
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    public void doGet() {
        System.out.println("GET请求！");
    }

    public void doPost() {
        System.out.println("POST请求！");
    }
}
