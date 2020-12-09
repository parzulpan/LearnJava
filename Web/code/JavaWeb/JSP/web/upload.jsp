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
