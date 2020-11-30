package cn.parzulpan.jdbc.ch02;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : 数据库的连接
 */

public class ConnectionTest {

    // 连接方式一
    // 显式出现了第三方数据库的 API
    @Test
    public void test1() {
        try {
            // 1. 提供 java.sql.Driver 接口实现类的对象
            Driver driver = new com.mysql.jdbc.Driver();

            // 2. 提供 url，指明具体操作的数据
            String url = "jdbc:mysql://localhost:3306/test";

            // 3. 提供 Properties 的对象，指明用户名和密码
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "root");

            // 4. 调用 driver 的 connect()，获取连接
            Connection connect = driver.connect(url, info);
            System.out.println(connect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 连接方式二
    // 使用反射实例化Driver，不在代码中体现第三方数据库的API，体现了面向接口编程思想。
    @Test
    public void test2() {
        try {
            // 1. 实例化 Driver
            String className = "com.mysql.jdbc.Driver";
            Class<?> clazz = Class.forName(className);
            Driver driver = (Driver) clazz.newInstance();

            // 2. 提供 url，指明具体操作的数据
            String url = "jdbc:mysql://localhost:3306/test";

            // 3. 提供 Properties 的对象，指明用户名和密码
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "root");

            // 4. 调用 driver 的 connect()，获取连接
            Connection connect = driver.connect(url, info);
            System.out.println(connect);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
        }
    }

    // 连接方式三
    // 使用 DriverManager
    @Test
    public void test3() {
        try {
            // 1. 三要素
            String driverName = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String password = "root";

            // 实例化 Driver
            Class<?> clazz = Class.forName(driverName);
            Driver driver = (Driver) clazz.newInstance();

            // 3. 注册驱动
            DriverManager.registerDriver(driver);

            // 4. 获取连接
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
        }
    }

    // 连接方式四
    // 不用显式注册驱动，DriverManager 的源码中已经存在静态代码块，实现了驱动的注册。
    @Test
    public void test4() {
        try {
            // 1. 三要素
            String driverName = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String password = "root";

            // 实例化 Driver
            Class<?> clazz = Class.forName(driverName);
            Driver driver = (Driver) clazz.newInstance();

            // 3. 获取连接
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
        }
    }

    // 连接方式五，推荐
    // 使用配置文件的方式保存配置信息，在代码中加载配置文件
    // 使用配置文件的好处：
    // 1. 实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码。
    // 2. 如果修改了配置信息，省去重新编译的过程。

    @Test
    public void test5() {
        try {
            // 1. 加载配置信息
            InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(is);

            // 2. 读取配置信息
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverName");

            // 3. 加载驱动
            Class.forName(driverClass);

            // 4. 获取连接
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
