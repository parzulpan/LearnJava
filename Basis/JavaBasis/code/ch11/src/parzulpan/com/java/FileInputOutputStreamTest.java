package parzulpan.com.java;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 文件流-文件字节流操作
 */

public class FileInputOutputStreamTest {

    // 实现对 excel 文件的复制
    @Test
    public void testFileFileInputOutputStream() {
        File srcFile = new File("test.xlsx");
        File destFile = new File("test1.xlsx");
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            int data;

//            // 读取单个字符
//            while ((data = fis.read()) != -1) {
//                fos.write(data);
//            }

            // 将字符读入数组
            byte[] buffer = new byte[1024];
            while ((data = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
