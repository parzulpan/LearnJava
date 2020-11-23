package com.parzulpan.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 异常处理机制：try-catch-finally
 */

public class ExceptionTest {
    public static void main(String[] args) {
        // 最初版
//        try {
//            File file = new File("hello.txt");
//            FileInputStream fileInputStream = new FileInputStream(file);
//
//            int data = fileInputStream.read();
//            while (data != -1) {
//                System.out.println((char) data);
//                data = fileInputStream.read();
//            }
//            fileInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 改进版
        FileInputStream fileInputStream = null;
        try {
            File file = new File("hello.txt");
            fileInputStream = new FileInputStream(file);

            int data = fileInputStream.read();
            while (data != -1) {
                System.out.println((char) data);
                data = fileInputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
