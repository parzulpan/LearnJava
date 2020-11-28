package parzulpan.com.java;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc :
 */

public class InetAdressTest {
    public static void main(String[] args) {
        InetAddress inet1 = null;
        InetAddress inet2 = null;
        InetAddress inet3 = null;
        InetAddress inet4 = null;
        try {
            inet1 = InetAddress.getByName("www.parzulpan.cn");
            System.out.println(inet1);

            inet2 = InetAddress.getByName("61.135.185.32");
            System.out.println(inet2);

            inet3 = InetAddress.getByName("localhost");
            System.out.println(inet3);

            inet4 = InetAddress.getLocalHost();
            System.out.println(inet4);

            System.out.println(inet1.getHostAddress());
            System.out.println(inet1.getHostName());
            boolean reachable = false;
            try {
                reachable = inet1.isReachable(1000);
                System.out.println(reachable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



    }
}
