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
 * 例子3：从客户端发送文件给服务端，服务端保存到本地。并返回“发送成功”给客户端。并关闭相应的连接。
 */

public class TCPTest2 {

    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        try {
            // 创建 Socket 对象，指明服务器端的 IP 和 Port
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 29998);

            // 获取一个输出流，用于输出数据
            os = socket.getOutputStream();
            // 获取一个输入流，用于输入数据
            is = socket.getInputStream();

            // 写出和读入数据
            bis = new BufferedInputStream(new FileInputStream(new File("tcp.png")));
            baos = new ByteArrayOutputStream();

            //
            byte[] buffer1 = new byte[1024];
            int data1;
            while ((data1 = bis.read(buffer1)) != -1) {
                os.write(buffer1, 0, data1);
            }
            // 关闭数据的输出，结束阻塞式的等待
            socket.shutdownOutput();

            byte[] buffer2 = new byte[1024];
            int data2;
            while ((data2 = is.read(buffer2)) != -1) {
                baos.write(buffer2, 0, data2);
            }
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
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
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            ss = new ServerSocket(29998);
            socket = ss.accept();

            is = socket.getInputStream();
            os = socket.getOutputStream();

            bos = new BufferedOutputStream(new FileOutputStream(new File("tcpServer1.png")));

            byte[] buffer1 = new byte[1024];
            int data1;
            while ((data1 = is.read(buffer1)) != -1) {
                bos.write(buffer1, 0, data1);
            }

            System.out.println("图片传输完成！");
            os.write("服务器端已经接收到客户端发送的图片！".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (os != null) {
                    os.close();
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
