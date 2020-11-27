package parzulpan.com.exer;

import java.io.File;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-27
 * @Desc : 遍历指定目录所有文件名称，包括子文件目录中的文件，并计算指定目录占用空间的大小，并删除指定文件目录及其下的所有文件？
 */

public class GetAllFile {
    public static void main(String[] args) {
        System.out.print("请输入目录：");
        Scanner scanner = new Scanner(System.in);
        String dir = scanner.next();
        File file = new File(dir);
        System.out.println("目录占用空间的大小（字节数）：" + getDir(file));

        System.out.print("请输入目录：");
        Scanner scanner1 = new Scanner(System.in);
        String dir1 = scanner1.next();
        File file1 = new File(dir1);
        deleteDir(file1);

    }

    public static long getDir(File dir) {
        File[] subFiles = dir.listFiles();
        long len = 0;
        for (File file : subFiles) {
            if (file.isDirectory()) {
                len += getDir(file);
            } else {
                String absolutePath = file.getAbsolutePath();
                System.out.println(absolutePath);
                len += file.length();
            }
        }
        return len;
    }

    public static void deleteDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteDir(f);
            }
        }
        file.delete();
    }
}
