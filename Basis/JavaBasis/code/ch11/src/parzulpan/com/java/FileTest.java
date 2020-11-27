package parzulpan.com.java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : File 类的使用
 */

public class FileTest {

    @Test
    public void test1(){

        File file1 = new File("hello.txt");  // 相对于当前 module
        File file2 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code/ch11" + File.separator + "hi.txt");

        System.out.println(file1.getAbsolutePath());
        System.out.println(file1.getPath());
        System.out.println(file1.getName());
        System.out.println(file1.length());
        System.out.println(new Date(file1.lastModified()));

        System.out.println();

        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.getPath());
        System.out.println(file2.getName());
        System.out.println(file2.length());
        System.out.println(file2.lastModified());

        System.out.println();

        File file3 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code");
        String[] list = file3.list();
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println();

        File[] files = file3.listFiles();
        for (File f: files) {
            System.out.println(f);
        }
    }

    @Test
    public void test2() {
        File file1 = new File("hello.txt");  // 相对于当前 module
        File file2 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code" + File.separator + "hi.txt");
        File file3 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code");

        boolean b = file1.renameTo(file2);
        System.out.println(b);

        System.out.println();
        System.out.println(file3.isDirectory());
        System.out.println(file1.isFile());
        System.out.println(file1.exists());
        System.out.println(file3.exists());
        System.out.println(file1.canRead());
        System.out.println(file1.canWrite());
        System.out.println(file1.canExecute());
        System.out.println(file1.isHidden());
    }

    @Test
    public void test3() {
        File file1 = new File("helloW.txt");  // 相对于当前 module
        if (!file1.exists()) {
            try {
                boolean newFile = file1.createNewFile();
                System.out.println("创建文件 " + newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            boolean delete = file1.delete();
            System.out.println("删除文件 " + delete);
        }

        File file2 = new File("/Users/parzulpan/Study/LearnJava/Basis/JavaBasis/code/newDir");
        if (!file2.exists()) {
            boolean mkdirs = file2.mkdirs();
            System.out.println("创建文件夹 " + mkdirs);
        } else {
            boolean delete = file2.delete();
            System.out.println("删除文件夹 " + delete);
        }
    }
}
