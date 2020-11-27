package parzulpan.com.java;

import java.io.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 标准输入输出流、打印流、数据流
 */

public class OtherStreamTest {
    public static void main(String[] args) {
        test2();
    }


    /**
     * 1. 标准输入输出流
     * 1.1 System.in 标准的输入流，默认从键盘输入，System.out 标准的输出流，默认从控制台输出
     * 1.2 System.setIn(InputStream is) 和 System.setOut(OutputStream ps) 方式重新指定输入和输出的流
     *
     * 练习：从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，直至当输入 “e” 或者 “exit” 时，退出程序。
     * 方法一：使用 Scanner；
     * 方法二：使用 System.in； -> System.in -> 转换流 -> BufferedReader.readLine
     */
    public static void test() {
        System.out.print("请输入信息（e 或 exit 退出）：");

        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);

            String s;
            while ((s = br.readLine()) != null) {
                if ("e".equalsIgnoreCase(s) || "exit".equalsIgnoreCase(s)) {
                    System.out.print("已安全退出！");
                    break;
                }
                System.out.println(s.toUpperCase());
                System.out.print("请输入信息（e 或 exit 退出）：");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 打印流
     * 实现将基本数据类型的数据格式转化为字符串输出，提供了一系列重载的 print() 和 println() 方法，用于多种数据类型的输出
     * 练习：将打印输出流不通过控制台，而是直接写入到文件
     */
    public static void test1() {
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("ASCII.txt"));
            //  创建打印输出流,设置为自动刷新模式（写入换行符或字节 '\n' 时都会刷新输出缓冲区）
            ps = new PrintStream(fos, true);
            if (ps != null) {
                System.setOut(ps);  // 把标准输出流(控制台输出)改成文件
            }

            for (int i = 0; i < 256; i++) {
                System.out.print((char) i);
                if (i % 50 == 0) {
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (ps != null) {
            ps.close();
        }
    }

    /**
     * 3. 数据流
     * 为了方便地操作 Java 语言的 基本数据类型 和 String 的数据，可以使用数据流。
     * 数据流有两个类：(用于读取和写出基本数据类型、String类的数据）
     * DataInputStream 和 DataOutputStream 分别“套接”在 InputStream 和 OutputStream 子类的流上
     */
    public static void test2() {
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            dos = new DataOutputStream(new FileOutputStream("destData.dat"));
            dis = new DataInputStream(new FileInputStream("destData.dat"));

            dos.writeUTF("Beijing");
            dos.writeBoolean(true);
            dos.writeLong(1008611);

            String info = dis.readUTF();
            boolean b = dis.readBoolean();
            long l = dis.readLong();
            System.out.println(info);
            System.out.println(b);
            System.out.println(l);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (dos != null) {
                dos.close();
            }
            if (dis != null) {
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
