package parzulpan.com.java;

import org.junit.Test;

import java.io.*;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 文件流-文件字符流操作
 */

public class FileReaderWriter {
    public static void main(String[] args) {
        File file = new File("hello.txt");  // 相较于当前工程
    }

    // 将 hello.txt 读入到内存程序中，并输出
    @Test
    public void testFileReader(){
        // 1.
        File file = new File("hello.txt");  // 相较于当前Module
        FileReader fileReader = null;

        try {
            // 2.
            fileReader = new FileReader(file);

            char[] chars = new char[5];
            int data;

            // 3.
            while (true) {
//                // int read()
//                if ((data = fileReader.read()) == -1) break;
//                System.out.print((char) data);

                // int read(char[] buf)
                if ((data = fileReader.read(chars)) == -1) break;
                for (int i = 0; i < data; ++i) {    // 注意这里是 data
                    System.out.print(chars[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4.
        try {
            if (fileReader != null) {
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将内存程序数据 写入 hello1.txt
    @Test
    public void testFileWrite() {
        // 1.
        File file = new File("hello1.txt");
        FileWriter fileWriter = null;

        try {
            // 2.
            fileWriter = new FileWriter(file, true);    // append 为 true 则追加，否则覆盖

            // 3.
            fileWriter.write("hello world!!!!!");
            fileWriter.write("yes!!".toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    // 4.
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
