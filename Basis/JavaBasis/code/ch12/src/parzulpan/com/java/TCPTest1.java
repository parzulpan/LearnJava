package parzulpan.com.java;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : TCP 网络编程
 * 例子2：客户端发送文件给服务端，服务端将文件保存在本地。
 */

public class TCPTest1 {

    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        try {
            // 1.
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 29999);

            // 2.
            os = socket.getOutputStream();

            // 3.
            bis = new BufferedInputStream(new FileInputStream(new File("tcp.png")));

            // 4.
            byte[] buffer = new byte[1024];
            int data;
            while ((data = bis.read(buffer)) != -1) {
                os.write(buffer, 0, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5.
            try {
                if (bis != null) {
                    bis.close();
                }
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

    @Test
    public void server() {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            // 1.
            ss = new ServerSocket(29999);

            //2.
            socket = ss.accept();

            // 3.
            is = socket.getInputStream();

            // 4.
            bos = new BufferedOutputStream(new FileOutputStream(new File("tcpServer.png")));
            byte[] buffer = new byte[1024];
            int data;
            while ((data = is.read(buffer)) != -1) {
                bos.write(buffer, 0, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5.
            try {
                if (bos != null) {
                    bos.close();
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
