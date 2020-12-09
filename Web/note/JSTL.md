# JSTL 标签库

## 简介

JSTL（JSP Standard Tag Library），即 JSP 标准标签库。标签库是为了替换代码脚本，使得整个 jsp 页面变得更加简洁。

JSTL 有五个功能不同的**标签库**组成：

功能范围 | URI | 前缀
:--- | :--- | :---
**核心标签库（重点）** | `http://java.sun.com/jsp/jstl/core` | c
格式化 | `http://java.sun.com/jsp/jstl/fmt` | fmt
函数 | `http://java.sun.com/jsp/jstl/functions` | fn
数据库（不使用） | `http://java.sun.com/jsp/jstl/sql` | sql
XML（不使用） | `http://java.sun.com/jsp/jstl/xml` | x

在 jsp 标签库中使用 `taglib` 指令引入标签库：

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
```

## 使用

使用步骤：

* 先导入 jstl 标签的 jar 包：`taglibs-standard-impl-1.2.1.jar` `taglibs-standard-spec-1.2.1.jar`
* 使用 taglib 指令引入标签库：`<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`

**`<c:set />`**：set 标签可以往域中保存数据

```jsp
    <%--
    i.<c:set />
    作用：set 标签可以往域中保存数据
    域对象.setAttribute(key,value);

    scope 属性设置保存到哪个域
    page 表示 PageContext 域（默认值）
    request 表示 Request 域
    session 表示 Session 域
    application 表示 ServletContext 域

    var 属性设置 key 是多少
    value 属性设置值
    --%>

    保存之前：${ sessionScope.abc } <br>

    <c:set scope="session" var="abc" value="abcValue"/>

    保存之后：${ sessionScope.abc } <br>
```

**`<c:if />`**：if 标签用来做 if 判断

```jsp
    <%--
    ii.<c:if />
    if 标签用来做 if 判断。
    test 属性表示判断的条件（使用 EL 表达式输出）
    --%>

    <c:if test="${ 12 == 12 }">
        <h1>12 等于 12</h1>
    </c:if>
    <c:if test="${ 12 != 12 }">
        <h1>12 不等于 12</h1>
    </c:if>
```

**`<c:choose> <c:when> <c:otherwise>`**：多路判断，跟 `switch ... case .... default` 非常接近

```jsp
    <%--
    iii.<c:choose> <c:when> <c:otherwise>标签
    作用：多路判断。跟 switch ... case .... default 非常接近

    choose 标签开始选择判断
    when 标签表示每一种判断情况
    test 属性表示当前这种判断情况的值
    otherwise 标签表示剩下的情况

    <c:choose> <c:when> <c:otherwise>标签使用时需要注意的点：
    1、标签里不能使用 html 注释，要使用 jsp 注释
    2、when 标签的父标签一定要是 choose 标签
    --%>

    <%
        request.setAttribute("height", 180);
    %>

    <c:choose>
        <%-- 这是 html 注释 --%>
        <c:when test="${ requestScope.height > 190 }">
            <h2>小巨人</h2>
        </c:when>
        <c:when test="${ requestScope.height > 180 }">
            <h2>很高</h2>
        </c:when>
        <c:when test="${ requestScope.height > 170 }">
            <h2>还可以</h2>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${requestScope.height > 160}">
                    <h3>大于 160</h3>
                </c:when>
                <c:when test="${requestScope.height > 150}">
                    <h3>大于 150</h3>
                </c:when>
                <c:when test="${requestScope.height > 140}">
                    <h3>大于 140</h3>
                </c:when>
                <c:otherwise>
                    其他小于 140
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
```

**`<c:forEach />`**：遍历输出

```jsp
    <%--1.遍历 1 到 10，输出
    begin 属性设置开始的索引
    end 属性设置结束的索引
    var 属性表示循环的变量(也是当前正在遍历到的数据)
    for (int i = 1; i < 10; i++)
    --%>

    <table border="1">
        <c:forEach begin="1" end="10" var="i">
            <tr>
                <td>第${i}行</td>
            </tr>
        </c:forEach>
    </table>
```

## 文件的上传和下载

### 文件的上传

**实现步骤**：

* 客户端：
  * 发送 post 请求，告诉服务器要上传什么文件
* 服务器：
  * 要有一个 form 标签，method=post 请求，form 标签的 **encType** 属性值必须为 `multipart/form-data` 值
  * 在 form 标签中使用 `input type=file` 添加上传的文件
  * 接收并处理上传的文件

**文件上传时 HTTP 协议说明**：

* `Content-type` 表示提交的数据类型
  * `multipart/form-data` 表示提交的数据，以多段（每一个表单项代表一个数据段）的形式进行拼接，然后以**二进制流**的形式发送给服务器
  * `boundary` 表示每段数据的分隔符，它的值是有浏览器随机生成的，它是每段数据的分割符

实现上传下载功能常用**两个包**：

* `commons-fileupload.jar`
* `commons-io.jar`

这两个包的**常用类**：

* `ServletFileUpload` 用于解析上传的数据
* `FileItem` 表示每一个表单项

**常用方法**：

* `boolean ServletFileUpload.isMultipartContent(HttpServletRequest request)` 判断当前上传的数据格式是否是多段的格式，即是否是文件
* `public List<FileItem> parseRequest(HttpServletRequest request)` 解析上传的数据
* `boolean FileItem.isFormField()` 判断当前这个表单项，是否是普通的表单项，还是上传的文件类型，true 表示普通类型的表单项，false 表示上传的文件类型
* `String FileItem.getFieldName()` 获取表单项的 name 属性值
* `String FileItem.getString()` 获取当前表单项的值
* `String FileItem.getName()` 获取上传的文件名
* `void FileItem.write(File file)` 将上传的文件写到 参数 file 所指向的位置

```jsp
<%--
  Created by IntelliJ IDEA.
  User: parzulpan
  Date: 2020/12/9
  Time: 6:00 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件的上传</title>
</head>
<body>
    <form action="/JSP/uploadServlet" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="username"><br>
        头像：<input type="file" name="photo"><br>
        <input type="submit" value="上传">
    </form>
</body>
</html>
```

```java
package cn.parzulpan.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc : 文件上传
 */

@WebServlet(name = "UploadServlet", urlPatterns = ("/uploadServlet"))
public class UploadServlet extends HttpServlet {

    // 处理上传的文件
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("文件上传了");

        // 1. 先判断上传的数据是否有多段数据，只有是多段的数据，才是文件上传的
        if (ServletFileUpload.isMultipartContent(request)) {
            // 创建 FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);

            // 解析上传的数据，得到每一个表单项 FileItem
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                // 循环判断，每一个表单项，是普通类型，还是上传的文件
                for (FileItem fileItem: list) {
                    if (fileItem.isFormField()) {
                        System.out.println("表单项的 name 属性值：" + fileItem.getFieldName());
                        System.out.println("表单项的 value 属性值：" + fileItem.getString("UTF-8"));
                    } else {
                        // 上传的文件
                        System.out.println("表单项的 name 属性值：" + fileItem.getFieldName());
                        System.out.println("上传的文件名：" + fileItem.getName());

                        // 保存到本地

                        fileItem.write(new File(fileItem.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
```

### 文件的下载

**实现步骤**：

* 客户端：
  * 发送请求，告诉服务器要下载什么文件
* 服务器：
  * 获取要下载的文件名
  * 读取要下载的文件内容
  * 在回传前，通过响应头告诉客户端返回的**数据类型**以及告诉客户端收到的数据是**用于下载使用**

```java
package cn.parzulpan.servlet;

import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc : 文件下载
 */

@WebServlet(name = "DownloadServlet", urlPatterns = ("/downloadServlet"))
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // 处理文件的下载
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取要下载的文件名
        String downloadFileName = "cat.png";

        // 2. 读取要下载的文件内容，通过 ServletContext 对象
        ServletContext servletContext = getServletContext();

        // 获取要下载的文件信息
        String mimeType = servletContext.getMimeType("/file/" + downloadFileName);  //  文件类型

        // 3. 在回传前，通过响应头告诉客户端返回的**数据类型**以及告诉客户端收到的数据是**用于下载使用**
        // Content-Disposition 表示收到的数据怎么处理，attachment 表示附件
        response.setContentType(mimeType);  // 告诉数据类型
        // 解决中文乱码的问题
        if (request.getHeader("User-Agent").contains("Firefox")) {
            // 如果是火狐浏览器则使用 Base64 编码
            response.setHeader("Content-Disposition",
                    "attachment; fileName=?UTF-8?B?" +
                            new BASE64Encoder().encode("猫.png".getBytes(StandardCharsets.UTF_8)) + "?=" );
        } else {
            // 如果不是火狐浏览器，例如谷歌或者 IE，则使用 URL 编码
            System.out.println("attachment; fileName=" + URLEncoder.encode(downloadFileName, "UTF-8"));
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + URLEncoder.encode("猫.png", "UTF-8"));
        }

        // 4. 数据回传
        InputStream resourceAsStream = servletContext.getResourceAsStream("/file/" + downloadFileName); // 获取输入流
        ServletOutputStream outputStream = response.getOutputStream();  // 获取响应的输出流
        IOUtils.copy(resourceAsStream, outputStream);   // 读取输入流中的全部数据，复制给输出流，输出给客户端

    }
}
```

## 练习和总结
