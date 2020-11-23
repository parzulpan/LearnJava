package com.parzulpan.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-22
 * @Desc : 异常处理机制：throws + 异常类型
 */

public class ExceptionTest1 {
    public static void main(String[] args) {

        try {
            method();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void method() throws IOException {
        File file = new File("hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        int data = fileInputStream.read();
        while (data != -1) {
            System.out.println((char) data);
            data = fileInputStream.read();
        }

        fileInputStream.close();
    }
}
