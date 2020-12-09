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
