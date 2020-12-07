# 获取数据库连接

## 要素一：Driver 接口实现类

**Driver 接口**：

* `java.sql.Driver` 接口是所有 JDBC 驱动程序需要实现的接口。这个接口是提供给数据库厂商使用的，不同数据库厂商提供不同的实现。
* 在程序中不需要直接去访问实现了 Driver 接口的类，而是由驱动程序管理器类（`java.sql.DriverManager`）去调用这些 Driver 实现。
  * Oracle的驱动：**oracle.jdbc.driver.OracleDriver**
  * MySQL的驱动： **com.mysql.jdbc.Driver**

**加载并注册 JDBC 驱动**：

* 加载驱动：加载 JDBC 驱动需调用 Class 类的静态方法 forName()，向其传递要加载的 JDBC 驱动的类名
  * **Class.forName(“com.mysql.jdbc.Driver”);**
* 注册驱动：DriverManager 类是驱动程序管理器类，负责管理驱动程序
  * **使用 DriverManager.registerDriver(com.mysql.jdbc.Driver) 来注册驱动**
  * 通常**不用显式**调用 DriverManager 类的 registerDriver() 方法来注册驱动程序类的实例，因为 Driver 接口的驱动程序类**都**包含了静态代码块，在这个静态代码块中，会调用 DriverManager registerDriver() 方法来注册自身的一个实例。

## 要素二：URL

JDBC URL 用于标识一个被注册的驱动程序，驱动程序管理器通过这个 URL 选择正确的驱动程序，从而建立到数据库的连接。

JDBC URL的标准由三部分组成，各部分间用冒号分隔：

* **协议**：JDBC URL 中的协议总是 jdbc；
* **子协议**：子协议用于标识一个数据库驱动程序；
* **子名称**：一种标识数据库的方法。子名称可以依不同的子协议而变化，用子名称的目的是为了**定位数据库**提供足够的信息。包含**主机名**（对应服务端的 ip 地址），**端口号，数据库名**；

**常用 URL**：

* MySQL 的连接 URL 编写方式：
  * `jdbc:mysql://主机名称:mysql服务端口号/数据库名称?参数=值&参数=值`
  * `jdbc:mysql://localhost:3306/test`
  * `jdbc:mysql://localhost:3306/test**?useUnicode=true&characterEncoding=utf8`（如果JDBC程序与服务器端的字符集不一致，会导致乱码，那么可以通过参数指定服务器端的字符集）
  * `jdbc:mysql://localhost:3306/test?user=root&password=123456`
* Oracle 9i 的连接 URL 编写方式：
  * `jdbc:oracle:thin:@主机名称:oracle服务端口号:数据库名称`
  * `jdbc:oracle:thin:@localhost:1521:test`
* SQLServer 的连接 URL 编写方式：
  * `jdbc:sqlserver://主机名称:sqlserver服务端口号:DatabaseName=数据库名称`
  * `jdbc:sqlserver://localhost:1433:DatabaseName=test`

## 要素三：用户名和密码

* user、password 可以用“属性名=属性值”方式告诉数据库；
* 可以调用 DriverManager 类的 getConnection() 方法建立到数据库的连接。

## 连接方式举例

```java
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
```

总结，推荐连接方式五，使用配置文件的方式保存配置信息，在代码中加载配置文件。使用配置文件的好处：

* 实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码。
* 如果修改了配置信息，省去重新编译的过程。

## 练习和总结
