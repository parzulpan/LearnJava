package parzulpan.com.java;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-11-28
 * @Desc : 了解类的加载器
 */

public class ClassLoaderTest {

    // 类加载器
    @Test
    public void test1() {
        // 对于自定义类，获取一个系统类加载器
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);

        // 获取系统类加载器的父类加载器，即扩展类加载器
        ClassLoader parent = classLoader.getParent();
        System.out.println(parent);

        // 获取扩展类加载器的父类加载器，即引导类加载器
        // 无法获取引导类加载器，它主要负责加载 Java 的核心类库，无法加载自定义类
        ClassLoader parent1 = parent.getParent();
        System.out.println(parent1);    // null

        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);   // null

        ClassLoader classLoader2 = null;
        try {
            classLoader2 = Class.forName("java.lang.Object").getClassLoader();  // null，说明也是引导类加载器
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(classLoader2);
    }

    @Test
    // 读取配置文件
    public void test2() {
        Properties properties = new Properties();

        try {
            // 读取配置文件方式一：使用 IO 流
            // 此时文件默认在当前 module 下
            FileInputStream fis = new FileInputStream("jdbc.properties");
            properties.load(fis);

            // 读取配置文件方式二：使用类加载器
            // 此时文件默认在当前 module 的 src 下
            ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
            InputStream rss = classLoader.getResourceAsStream("jdbc1.properties");
            properties.load(rss);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        System.out.println("user = " + user + ", password = " + password);
    }
}
