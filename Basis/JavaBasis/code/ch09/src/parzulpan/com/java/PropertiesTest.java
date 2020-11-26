package parzulpan.com.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc : Properties 处理配置文件
 */

public class PropertiesTest {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;

        try {
            Properties properties = new Properties();

            fileInputStream = new FileInputStream("jdbc.properties");
            properties.load(fileInputStream);

            String name = properties.getProperty("name");
            String password = properties.getProperty("password");

            System.out.println("name = " + name + ", password = " + password);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
