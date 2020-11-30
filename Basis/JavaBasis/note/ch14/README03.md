# Java11 新特性

## 新增字符串处理方法

新增方法：

* 判断字符串是否为空白 `" ".isBlank(); // true`
* 去除首尾空白 `" Javastack ".strip(); // "Javastack"`
* 去除尾部空格 `" Javastack ".stripTrailing(); // " Javastack"`
* 去除首部空格 `" Javastack ".stripLeading(); // "Javastack "`
* 复制字符串 `"Java".repeat(3);// "JavaJavaJava"`
* 行数统计 `"A\nB\nC".lines().count(); // 3`

## Optional 加强

很方便的将一个 Optional 转换成一个 Stream, 或者当一个空 Optional 时给它一个替代的。

## 局部变量类型推断升级

在 Java11 中，在 var 上可以添加注解的语法格式。

```java
//错误的形式: 必须要有类型, 可以加上var
//Consumer<String> con1 = (@Deprecated t) ->
System.out.println(t.toUpperCase());

//正确的形式:
//使用var的好处是在使用lambda表达式时给参数加上注解。
Consumer<String> con2 = (@Deprecated var t) ->
System.out.println(t.toUpperCase());
```

## 全新的 HTTP 客户端 API

HTTP，用于传输网页的协议，早在 **1997** 年就被采用在目前的 1.1 版本中。直到 **2015** 年，**HTTP2** 才成为标准。

**HTTP/1.1** 和 **HTTP/2** 的**主要区别**是**如何在客户端和服务器之间构建和传输数据**。

* HTTP/1.1 依赖于请求/响应周期;
* HTTP/2 允许服务器 “push” 数据：它可以发送比客户端请求更多的数据。这使得它可以优先处理并发送对于首先加载网页至关重要的数据。

在 Java11 中，增加了处理 HTTP 请求的的 HTTP Client API，该 API 支持同步和异步。它将**替代仅适用于 blocking 模式的 HttpURLConnection**
（HttpURLConnection是在HTTP 1.0的时代创建的，并使用了协议无关的方法），并提供对 **WebSocket** 和 **HTTP/2** 的支持。

```java
package parzulpan.com.java11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java11 全新的 HTTP 客户端 API
 */

public class HttpTest {
    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:8080/test/")).build();
            HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();

            // 同步
            HttpResponse<String> response = client.send(request, responseBodyHandler);
            String body = response.body();
            System.out.println(body);

            // 异步
            CompletableFuture<HttpResponse<String>> sendAsync = client.sendAsync(request, responseBodyHandler);
            sendAsync.thenApply(HttpResponse::body).thenAccept(System.out::println);
            HttpResponse<String> response1 = sendAsync.get();
            String body1 = response1.body();
            System.out.println(body1);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```

## 更简化的编译运行程序

要运行一个 Java 源代码必须先编译，再运行，两步执行动作。
在 Java 11 中，通过一个 java 命令就直接搞定了，如以下所示：`java Javastack.java`。

一个命令编译运行源代码的注意点：

* 执行源文件中的第一个类, 第一个类必**须包含主方法**。
* 并且不可以使用其它源文件中的自定义类, 本文件中的自定义类是可以使用的。

## 废弃 Nashorn 引擎

在 Java 11 中，废除 Nashorn javascript 引擎，在后续版本准备移除掉，有需要的可以考虑使用 GraalVM。

## ZGC

ZGC（A Scalable Low-Latency Garbage Collector，**可扩展的低延迟垃圾收集器**），是一个并发, 基于 region, 压缩型的垃圾收集器, 只有 root 扫描阶段会 `STW(stop the world)`, 因此 GC 停顿时间不会随着堆的增长和存活对象的增长而变长。

优势：

* GC 暂停时间不会超过 10ms；
* 既能处理几百兆的小堆, 也能处理几个 T 的大堆；
* 和 G1 相比, 应用吞吐能力不会下降超过 15%；
* 为未来的 GC 功能和利用 colord 指针以及 Load barriers 优化奠定基础；
* 初始只支持 64 位系统；

## 总结和练习
