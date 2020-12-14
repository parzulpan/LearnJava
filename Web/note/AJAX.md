# AJAX 请求

## 什么是 AJAX

AJAX（Asynchronous JavaScript And XMl），即**异步** JS 和 XML。是指一种创建交互式网页应用的网页开发技术。

AJAX 是一种浏览器通过 JS 异步发起请求，局部更新页面的技术。它请求的**局部更新**，浏览器地址不会发生变化，且局部更新不会舍弃原来的页面。

## 原生 AJAX 示例

```java
package cn.parzulpan.web;

import cn.parzulpan.bean.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-14
 * @Desc :
 */

@WebServlet(name = "AjaxServlet", urlPatterns = ("/ajaxServlet"))
public class AjaxServlet extends BaseServlet {
    protected void javaScriptAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Ajax 请求发过来了");

        // 将数据返回给客户端
        User user = new User(1, "潘K", "123456", "parzulpan@321.com");
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);
        response.getWriter().write(userJsonString);
    }

}
```

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>原生 AJAX 请求的示例</title>
		<script type="text/javascript">
			// 在这里使用 javaScript 语言发起 Ajax 请求，访问服务器 AjaxServlet 中 javaScriptAjax
			function ajaxRequest() {
				//1、首先要创建 XMLHttpRequest
				let xmlHttpRequest = new XMLHttpRequest();

				//2、调用 open 方法设置请求参数
				xmlHttpRequest.open("GET", "ajaxServlet?action=javaScriptAjax", true);

				//4、在 send 方法前绑定 onreadystatechange 事件，处理请求完成后的操作
				xmlHttpRequest.onreadystatechange = function () {
					if (xmlHttpRequest.readyState === 4 && xmlHttpRequest.status === 200) {
						let jsonObj = JSON.parse(xmlHttpRequest.responseText);
						// 把响应的数据显示在页面上
						document.getElementById("div01").innerHTML = " id: " + jsonObj.id +
								" username: " + jsonObj.username +
								" password: " + jsonObj.password +
								" email: " + jsonObj.email
					}
				};

				//3、调用 send 方法发送请求
				xmlHttpRequest.send();
			}
		</script>
	</head>
	<body>	
		<button onclick="ajaxRequest()">ajax request</button>
		<div id="div01">
		</div>
	</body>
</html>
```

## jQuery 中的 AJAX 请求

**常用方法**：

* `$.ajax()`
  * `url` 表示请求的地址
  * `type` 表示请求的类型（GET、POST）
  * `data` 表示发送给服务器的数据，有两种格式：
    * `name=value&name2=value2`
    * `{key: value}`
  * `success` 请求成功，响应的回调函数
  * `dataType` 响应的数据类型，常用的有：
    * `text` 纯文本
    * `xml` xml 数据
    * `json` json 对象
* `$.get()` 和 `$.post()`
  * `url` 请求的 url 地址
  * `data` 发送的数据
  * `callback` 成功的回调函数
  * `type` 返回的数据类型
* `$.getJSON()`
  * `url` 请求的 url 地址
  * `data` 发送给服务器的数据
  * `callback` 成功的回调函数
* `serialize()` 可以把表单中所有表单项的内容都获取到，并以  `name=value&name=value` 的形式进行拼接

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>jQuery 中的 AJAX 请求</title>
		<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
		<script type="text/javascript">
			$(function(){
				// ajax请求
				$("#ajaxBtn").click(function(){
					
					$.ajax({
						url: "ajaxServlet",
						data: {action: "jQueryAjax"},
						success: function (data) {
							// 把响应的数据显示在页面上
							$("#msg").html(" id: " + data.id +
									" username: " + data.username +
									" password: " + data.password +
									" email: " + data.email);
						},
						dataType: "json"
					});
				});

				// ajax--get请求
				$("#getBtn").click(function(){
					$.get("ajaxServlet", "action=jQueryAjax", function (data) {
						// 把响应的数据显示在页面上
						$("#msg").html(" id: " + data.id +
								" username: " + data.username +
								" password: " + data.password +
								" email: " + data.email);
					}, "json")
				});
				
				// ajax--post请求
				$("#postBtn").click(function(){
					$.post("ajaxServlet", "action=jQueryAjax", function (data) {
						// 把响应的数据显示在页面上
						$("#msg").html(" id: " + data.id +
								" username: " + data.username +
								" password: " + data.password +
								" email: " + data.email);
					}, "json")
				});

				// ajax--getJson请求
				$("#getJSONBtn").click(function(){
					$.getJSON("ajaxServlet", "action=jQueryAjax", function (data) {
						// 把响应的数据显示在页面上
						$("#msg").html(" id: " + data.id +
								" username: " + data.username +
								" password: " + data.password +
								" email: " + data.email);
					})

				});

				// ajax请求
				$("#submit").click(function(){


					// 把参数序列化
					$.getJSON("ajaxServlet", "action=jQueryAjaxSerialize&" + $("#form01").serialize(), function (data) {
						// 把响应的数据显示在页面上
						$("#msg").html(" id: " + data.id +
								" username: " + data.username +
								" password: " + data.password +
								" email: " + data.email);
					})
				});
				
			});
		</script>
	</head>
	<body>
		<div>
			<button id="ajaxBtn">$.ajax请求</button>
			<button id="getBtn">$.get请求</button>
			<button id="postBtn">$.post请求</button>
			<button id="getJSONBtn">$.getJSON请求</button>
		</div>

		<div id="msg">

		</div>

		<br/><br/>
		<form id="form01" >
			用户名：<input name="username" type="text" /><br/>
			密码：<input name="password" type="password" /><br/>
			下拉单选：<select name="single">
			  	<option value="Single">Single</option>
			  	<option value="Single2">Single2</option>
			</select><br/>
		  	下拉多选：
		  	<select name="multiple" multiple="multiple">
		    	<option selected="selected" value="Multiple">Multiple</option>
		    	<option value="Multiple2">Multiple2</option>
		    	<option selected="selected" value="Multiple3">Multiple3</option>
		  	</select><br/>
		  	复选：
		 	<input type="checkbox" name="check" value="check1"/> check1
		 	<input type="checkbox" name="check" value="check2" checked="checked"/> check2<br/>
		 	单选：
		 	<input type="radio" name="radio" value="radio1" checked="checked"/> radio1
		 	<input type="radio" name="radio" value="radio2"/> radio2<br/>
		</form>			
		<button id="submit">提交--serialize()</button>
	</body>
</html>
```

```java
package cn.parzulpan.web;

import cn.parzulpan.bean.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-14
 * @Desc :
 */

@WebServlet(name = "AjaxServlet", urlPatterns = ("/ajaxServlet"))
public class AjaxServlet extends BaseServlet {
    protected void jQueryAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("jQueryAjax 请求发过来了");

        // 将数据返回给客户端
        User user = new User(1, "潘K", "123456", "parzulpan@321.com");
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);
        response.getWriter().write(userJsonString);
    }

    protected void jQueryAjaxSerialize(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("jQueryAjaxSerialize 请求发过来了");

        // 获取客户端的请求
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        // 将数据返回给客户端
        User user = new User(1, "潘K", "123456", "parzulpan@321.com");
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);
        response.getWriter().write(userJsonString);
    }

}
```

## 练习和总结
