package parzulpan.com.java;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : TCP 网络编程
 * 例子1：客户端发送消息给服务端，服务端将数据显示在控制台上
 */

public class TCPTest {

    // 客户端
    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        try {
            // 1. 创建 Socket 对象，指明服务器端的 IP 和 Port
            InetAddress inet = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inet, 28888);

            // 2. 获取一个输出流，用于输出数据
            os = socket.getOutputStream();

            // 3. 写出数据
            os.write("我是客户端".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭资源
            try {
                if (os != null) {
                    os.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    // 服务端
    @Test
    public void server() {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            // 1. 创建服务器端的 ServerSocket，指明自己的端口号
            ss = new ServerSocket(28888);

            // 2. 调用 accept() 表示接收到来自于客户端的 Socket
            socket = ss.accept();

            // 3. 获取一个输入流，用于输入数据
            is = socket.getInputStream();

            // 4. 读取数据
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[5];
            int data;
            while ((data = is.read(buffer)) != -1) {
                baos.write(buffer, 0, data);
            }
            System.out.println(baos.toString());
            System.out.println("数据来自于：" + socket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭资源
            try {
                if (baos != null) {
                    baos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (socket != null) {
                    socket.close();
                }
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
