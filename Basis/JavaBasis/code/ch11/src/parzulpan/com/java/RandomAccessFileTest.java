package parzulpan.com.java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 随机存取文件流的使用
 */

public class RandomAccessFileTest {

    @Test
    public void test1() {
        RandomAccessFile r = null;
        RandomAccessFile rw = null;
        try {
            //  以只读方式打开，则不会创建文件，而是会去读取一个已经存在的文件，如果读取的文件不存在则会出现异常
            r = new RandomAccessFile(new File("hello.txt"), "r");
            // 以读取和写入方式打开，如果文件不存在则会去创建文件，如果存在则不会创建。
            rw = new RandomAccessFile(new File("hello2.txt"),  "rw");

            byte[] bytes = new byte[1024];
            int data;
            while ((data = r.read(bytes)) != -1) {
                rw.write(bytes, 0, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (rw != null) {
                rw.close();
            }
            if (r != null) {
                r.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
