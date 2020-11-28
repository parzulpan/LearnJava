package parzulpan.com.java;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : URL 网络编程，从 tomcat 下载数据保存到本地
 */

public class URLTest1 {
    public static void main(String[] args) {

        HttpURLConnection urlConnection = null;
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL("http://localhost:8080/examples/cat.png?username=tomcat");

            urlConnection = (HttpURLConnection) url.openConnection();   // 针对 HTTP 协议的 URLConnection 类

            urlConnection.connect();

            is = urlConnection.getInputStream();
            bos = new BufferedOutputStream(new FileOutputStream(new File("ch12/tomcat.png")));

            byte[] buffer = new byte[1024];
            int data;
            while ((data = is.read(buffer)) != -1) {
                bos.write(buffer, 0, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
