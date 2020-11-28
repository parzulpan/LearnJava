package parzulpan.com.java;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : UDP 网络编程
 */

public class UDPTest {

    // 发送端
    @Test
    public void sender() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            byte[] str = "我是 UDP 方式发送的数据！".getBytes();

            DatagramPacket packet = new DatagramPacket(str, 0, str.length, InetAddress.getByName("127.0.0.1"), 28877);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    @Test
    public void receiver() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(28877);
            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String string = new String(packet.getData(), 0, packet.getLength());
            System.out.println(string + " -- " + packet.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }


    }
}
