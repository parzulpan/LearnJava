# JSP 页面

## 简介

JSP（Java Server Pages），即 Java 的服务器页面。它的主要作用是代替 Servlet 程序回传 HTML 页面的数据，因为 Servlet 程序回传 HTML 页面数据是一件非常繁锁的事情，开发成本和维护成本都极高。

```java
package cn.parzulpan.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc :
 */

@WebServlet(name = "PrintHtmlServlet", urlPatterns = ("/printHtmlServlet"))
public class PrintHtmlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 通过响应的回传流回传 html 页面数据
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE html>\r\n");
        writer.write(" <html lang=\"en\">\r\n");
        writer.write(" <head>\r\n");
        writer.write("  <meta charset=\"UTF-8\">\r\n");
        writer.write("  <title>Title</title>\r\n");
        writer.write(" </head>\r\n");
        writer.write(" <body>\r\n");
        writer.write("  这是 html 页面数据 \r\n");
        writer.write(" </body>\r\n");
        writer.write("</html>\r\n");
        writer.write("\r\n");
    }
}
```

```jsp
<%--
  Created by IntelliJ IDEA.
  User: parzulpan
  Date: 2020/12/9
  Time: 12:09 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    这是 html 页面数据
</body>
</html>

```

jsp 页面和 html 页面一样，都是存放在 web 目录下。访问也跟访问 html 页面一样。

## 本质

**JSP 页面本质上是一个 Servlet 程序**。

当我们第一次访问 jsp 页面的时候。Tomcat 服务器会帮我们把 jsp 页面翻译成为一个 java 源文件。并且对它进行编译成为 .class 字节码程序。

jsp 翻译出来的 java 类，它间接了继承了 HttpServlet 类。也就是说，翻译出来的是一个 Servlet 程序。其底层实现，也是通过输出流把 html 页面数据回传给客户端。

## 语法

### 头部的 page 指令

jsp 的 page 指令可以修改 jsp 页面中一些重要的属性，或者行为。

* `anguage 属性` 表示 jsp 翻译后是什么语言文件，暂时只支持 java
* `contentType 属性` 表示 jsp 返回的数据类型是什么。也是源码中  `response.setContentType()` 参数值
* `pageEncoding 属性` 表示当前 jsp 页面文件本身的字符集
* `import 属性` 跟 java 源代码中一样。用于导包，导类
* `autoFlush 属性` 设置当 out 输出流缓冲区满了之后，是否自动刷新冲级区。默认值是 true
* `buffer 属性` 设置 out 缓冲区的大小。默认是 8kb
* `errorPage 属性` 设置当 jsp 页面运行时出错，自动跳转去的错误页面路径
* `isErrorPage 属性` 设置当前 jsp 页面是否是错误信息页面。默认是 false。如果是 true 可以获取异常信息
* `session 属性` 设置访问当前 jsp 页面，是否会创建 HttpSession 对象。默认是 true
* `extends 属性` 设置 jsp 翻译出来的 java 类默认继承谁

### 三种脚本

**声明脚本**：

* 格式： `<%! 声明 java 代码 %>`
* 作用：可以给 jsp 翻译出来的 java 类定义属性和方法甚至是静态代码块、内部类等
* 声明类属性：
  
  ```jsp
  <%!
      private Integer id;
      private String name;
      private static Map<String,Object> map;
  %>
  ```

* 声明 static 静态代码块：

  ```jsp
  <%!
    static {
    map = new HashMap<String,Object>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
    }
  %>
  ```

* 声明类方法:

    ```jsp
    <%!
    public int abc(){
        return 12;
    }
    %>
    ```

* 声明内部类:

    ```jsp
    <%!
    public static class A {
        private Integer id = 12;
        private String abc = "abc";
    }
    %>
    ```

**表达式脚本**：

* 格式：`<%=表达式%>`
* 作用：在 jsp 页面上输出数据
* 特点：
  * 所有的表达式脚本都会被翻译到 `_jspService()` 方法中
  * 表达式脚本都会被翻译成为 `out.print()` 输出到页面上
  * 由于表达式脚本翻译的内容都在 `_jspService()` 方法中,所以 `_jspService()` 方法中的对象都可以直接使用
  * 表达式脚本中的表达式不能以分号结束

```jsp
<%=12 %> <br>
<%=12.12 %> <br>
<%="我是字符串" %> <br>
<%=map%> <br>
<%=request.getParameter("username")%>
```

**代码脚本**：

* 格式：`<% java 语句 %>`
* 作用：可以在 jsp 页面中，编写自己需要的功能，写的是 java 语句
* 特点：
  * 代码脚本翻译之后都在 `_jspService` 方法中
  * 代码脚本由于翻译到 `_jspService()` 方法中，所以在  `_jspService()` 方法中的现有对象都可以直接使用。
  * 还可以由多个代码脚本块组合完成一个完整的 java 语句
  * **代码脚本**还可以和**表达式脚本**一起组合使用，在 jsp 页面上输出数据

### 三种注释

* html 注释：`<!-- 这是 html 注释 -->`  html 注释会被翻译到 java 源代码中，在 `_jspService` 方法里，以 out.writer 输出到客户端
* java 注释：`// 单行 java 注释 /* 多行 java 注释 */` java 注释会被翻译到 java 源代码中
* jsp 注释：`<%-- 这是 jsp 注释 --%>` jsp 注释可以注掉 jsp 页面中所有代码

## 九大内置对象

jsp 中的内置对象，是指 Tomcat 在翻译 jsp 页面成为 Servlet 源代码后，内部提供的九大对象，叫内置对象。

* `request` 请求对象
* `response` 响应对象
* `pageContext` jsp 的上下文对象
* `session` 会话对象
* `application` ServletContext 对象
* `config` ServletConfig 对象
* `out` jsp 输出流对象
* `page` 指向当前 jsp 对象
* `exception` 异常对象

## 四大域对象

* `pageContext（PageContextImpl 类）` 当前 jsp 页面范围内有效
* `request（HttpServletRequest 类`） 一次请求内有效
* `session（HttpSession 类）` 一个会话范围内有效，打开浏览器访问服务器，直到关闭浏览器
* `application（ServletContext 类）` 整个 web 工程范围内都有效，只要 web 工程不停止，数据都在

域对象是可以像 Map 一样存取数据的对象。四个域对象功能一样，不同的是它们对数据的存取范围。

四个域在使用的时候，优先顺序分别是，它们从小到大的范围的顺序：`pageContext => request => session => application`

## 数据输出

* `response` 表示响应，经常用于设置返回给客户端的内容（输出）
* `out` 也是给用户做输出使用的

由于 jsp 翻译之后，底层源代码都是使用 `out` 来进行输出，所以一般情况下。我们在 jsp 页面中统一使用 `out` 来进行输出。避免打乱页面输出内容的顺序。在 jsp 页面中，可以统一使用 `out.print()` 来进行输出

## 常用标签

**静态包含**：

```jsp
<%--
<%@ include file=""%> 就是静态包含 file 属性指定你要包含的 jsp 页面的路径。地址中第一个斜杠 / 表示为 http://ip:port/工程路径/ 映射到代码的 web 目录
--%>
<%@ include file="/include/footer.jsp"%>
```

静态包含的**特点**：

* 静态包含不会翻译被包含的 jsp 页面
* 静态包含其实是把被包含的 jsp 页面的代码拷贝到包含的位置执行输出

**动态包含**：

```jsp
<%--
<jsp:include page=""></jsp:include> 这是动态包含
page 属性是指定你要包含的 jsp 页面的路径
动态包含也可以像静态包含一样。把被包含的内容执行输出到包含位置
--%>

<jsp:include page="/include/footer.jsp">
<jsp:param name="username" value="bbj"/>
<jsp:param name="password" value="root"/>
</jsp:include>
```

动态包含的**特点**：

* 动态包含会把包含的 jsp 页面也翻译成为 java 代码
* 动态包含底层代码使用如下代码去调用被包含的 jsp 页面执行输出：
`JspRuntimeLibrary.include(request, response, "/include/footer.jsp", out, false);`
* 动态包含，还可以传递参数

**请求转发**：

```jsp
<%--
<jsp:forward page=""></jsp:forward> 是请求转发标签，它的功能就是请求转发
page 属性设置请求转发的路径
--%>
<jsp:forward page="/scope2.jsp"></jsp:forward>
```

## Listener 监听器

Listener 监听器它是 JavaWeb 的三大组件之一，三大组件分别是 Servlet 程序、Filter 过滤器、Listener 监听器。

Listener 它是 JavaEE 的规范，就是接口。

**监听器的作用**：监听某种事物的变化。然后通过回调函数，反馈给客户（程序）去做一些相应的处理。

### ServletContextListener 监听器

* ServletContextListener 它可以监听 ServletContext 对象的创建和销毁
* ServletContext 对象在 web 工程启动的时候创建，在 web 工程停止的时候销毁
* 监听到创建和销毁之后都会分别调用 ServletContextListener 监听器的方法反馈：
  * `public void contextInitialized(ServletContextEvent sce);` 在 ServletContext 对象创建之后马上调用，做初始化
  * `public void contextDestroyed(ServletContextEvent sce);` 在 ServletContext 对象销毁之后调用

**使用步骤**：

* 编写一个类去实现 ServletContextListener
* 实现其两个回调方法
* 到 web.xml 中去配置监听器（或者用注解）

```java
package cn.parzulpan.listener;

/*
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc :
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class MyServletContextListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public MyServletContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        System.out.println("ServletContext 对象被创建了");
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        System.out.println("ServletContext 对象被销毁了");
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
    }
}
```

## 练习和总结

---
**在 jsp 页面中输出九九乘法口诀表？**

```jsp
<%--
  Created by IntelliJ IDEA.
  User: parzulpan
  Date: 2020/12/9
  Time: 11:38 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>九九乘法口诀表</title>
</head>
<body>
    <h1 align="center">九九乘法口诀表</h1>
    <table align="center">
    <% for (int i = 1; i <= 9; ++i) { %>
        <tr>
        <% for (int j = 1; j <= i; j++) { %>
            <td><%=j + "x" + i + "=" + (i*j)%></td>
        <% } %>
        </tr>
    <% } %>
    </table>

</body>
</html>

```

---
**jsp 输出一个表格，里面有 10 个学生信息？**

业务流程：

* 客户端：输入关键字搜索
* 服务器：
  * `SearchStudentServlet.java`：1. 获取请求的参数；2. 通过 DAO 到数据库中查询学生信息；3. 保存查询到的学生信息，保存在 request 域中；4. 请求转发到 jsp 页面；
  * `ShowStudent.jsp`：1. 从 request 域中获取学生信息；2. 遍历查询到的结果输出到客户端显示；

```java
package cn.parzulpan.bean;

/**
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc :
 */

public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;

    public Student() {
    }

    public Student(Integer id, String name, Integer age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
```

```java
package cn.parzulpan.servlet;

import cn.parzulpan.bean.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc :
 */

@WebServlet(name = "SearchStudentServlet", urlPatterns = ("/searchStudentServlet"))
public class SearchStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 模拟 DAO：使用 for 循环生成查询到的数据
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(i + 1, "name" + i + 1, 18 + i + 1, "男"));
        }

        // 保存查询到的结果到 request 域中
        request.setAttribute("students", students);

        // 请求转发到 ShowStudent.jsp 页面
        request.getRequestDispatcher("/ShowStudent.jsp").forward(request, response);
    }
}
```

```jsp
<%@ page import="cn.parzulpan.bean.Student" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: parzulpan
  Date: 2020/12/9
  Time: 11:58 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询信息</title>
    <style>
        table{
            border: 1px blue solid;
            width: 600px;
            border-collapse: collapse;
        }
        td,th{
            border: 1px blue solid;
        }
    </style>
</head>
<body>

    <%--获取 request 域中的数据--%>
    <%
        List<Student> studentList = (List<Student>) request.getAttribute("students");
    %>
    <table>
        <tr>
            <td>编号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>电话</td>
            <td>操作</td>
        </tr>
    <% for (Student student: studentList) { %>
        <tr>
            <td><%=student.getId()%></td>
            <td><%=student.getName()%></td>
            <td><%=student.getAge()%></td>
            <td><%=student.getSex()%></td>
            <td>删除、修改</td>
        </tr>
    <% } %>
    </table>
</body>
</html>
```

---
