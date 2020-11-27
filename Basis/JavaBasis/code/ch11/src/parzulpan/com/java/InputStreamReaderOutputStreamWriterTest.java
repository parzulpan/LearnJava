package parzulpan.com.java;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 转换流
 */

public class InputStreamReaderOutputStreamWriterTest {

    // 实现将字节的输入流按指定字符集转换为字符的输入流，等效于解码
    // 然后将字符的输出流按指定字符集转换为字节的输出流，等效于编码
    @Test
    public void test1() {
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            FileInputStream fis = new FileInputStream("word.txt");
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            FileOutputStream fos = new FileOutputStream("wordGBK.txt");
            osw = new OutputStreamWriter(fos, "GBK");

            char[] buffer = new char[1024];
            int data;
            while ((data = isr.read(buffer)) != -1) {
                String str = new String(buffer, 0, data);
                System.out.println(str);
                osw.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (isr != null) {
                isr.close();
            }
            if (osw != null) {
                osw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
