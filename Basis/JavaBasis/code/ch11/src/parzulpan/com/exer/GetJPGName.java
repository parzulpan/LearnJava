package parzulpan.com.exer;

import java.io.File;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 判断指定目录下是否有后缀名为 .jpg 的文件，如果有，就输出该文件名称？
 */

public class GetJPGName {
    public static void main(String[] args) {
        System.out.print("请输入目录：");
        Scanner scanner = new Scanner(System.in);
        String dir = scanner.next();
        printJPGtName(dir);
    }

    public static void printJPGtName(String dir) {
        File file = new File(dir);
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String l : list) {
                    boolean b = l.toLowerCase().endsWith(".jpg");
                    if (b) {
                        System.out.println(l);
                    }
                }
            }
        }
    }
}
