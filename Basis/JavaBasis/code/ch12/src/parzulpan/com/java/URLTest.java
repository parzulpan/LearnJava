package parzulpan.com.java;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : URL 网络编程
 */

public class URLTest {
    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/examples/tcp.png?username=tomcat");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null) {
            System.out.println("getProtocol() : "+url.getProtocol());
            System.out.println("getHost() : "+url.getHost());
            System.out.println("getPort() : "+url.getPort());
            System.out.println("getPath() : "+url.getPath());
            System.out.println("getFile() : "+url.getFile());
            System.out.println("getQuery() : "+url.getQuery());
        }
    }
}
