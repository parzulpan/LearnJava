# 数据库连接池

## 传统模式

使用数据库的传统模式：

* 在主程序（servlet、beans等）中建立数据库连接；
* 进行 SQL 操作；
* 断开数据库连接。

这种模式存在的问题：

* JDBC 连接数据库的方式（**四个步骤：加载配置、读取配置、加载驱动、获取连接**），会消耗大量的资源和时间，且连接资源没有得到很好的重复利用；
* 获取一次数据库连接，使用完成后都得关闭连接；
* 不能控制被创建的连接对象数。

## 数据库连接池技术

**基本思想**：为数据库连接建立一个“缓冲池”，预先在缓冲池中放入一定数量的连接，当需要建立数据库连接是，只需要从“缓冲池”中取出一个，使用完毕之后再放回去。

数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复利用一个现有的数据库连接，而不是重新建立一个。

数据库连接池在初始化时将创建一定数量的数据库连接放到连接池中，这些数据库连接的数量是由**最小数据库连接数来设定**的。无论这些数据库连接是否被使用，连接池都将一直保证至少拥有这么多的连接数量。连接池的**最大数据库连接数量**限定了这个连接池能占有的最大连接数，当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待队列中。

数据库连接池的优点：

* 资源重用；
* 更快的响应速度和更少的资源消耗；
* 统一的连接管理，避免数据库连接泄露。

## 开源的数据库连接池

JDBC 的数据库连接池使用 `javax.sql.DataSource` 来表示，DataSource 只是一个**接口**，该接口通常由服务器（Weblogic, WebSphere, Tomcat）提供实现，也有一些**开源组织**提供实现。

DataSource 通常被称为数据源，它包含连接池和连接池管理两个部分。

**DataSource 用来取代 DriverManager 来获取 Connection，获取速度快，同时可以大幅度提高数据库访问速度。**

**注意**：

* 数据源和数据库连接不同，数据源无需创建多个，它是产生数据库连接的工厂，因此**整个应用只需要一个数据源即可**。
* 当数据库访问结束后，程序还是像以前一样关闭数据库连接：`connnection.close()`，但它并没有关闭数据库的物理连接，它仅仅把数据库连接释放，归还给了数据库连接池。

开源的数据库连接池：

* **DBCP** 是Apache提供的数据库连接池。tomcat 服务器自带 dbcp 数据库连接池。**速度相对 c3p0 较快**，但因自身存在 BUG，Hibernate3 已不再提供支持。
* **C3P0** 是一个开源组织提供的一个数据库连接池，**速度相对较慢，稳定性还可以** 。hibernate 官方推荐使用。
* Proxool 是sourceforge下的一个开源项目数据库连接池，有监控连接池状态的功能，**稳定性较 c3p0 差一点**。
* BoneCP 是一个开源组织提供的数据库连接池，速度快。
* **[Druid](https://github.com/alibaba/druid)** 是阿里提供的数据库连接池，据说是 集DBCP 、C3P0 、Proxool 优点于一身的数据库连接池。

---

```java
package cn.parzulpan.jdbc.ch08;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : c3p0 数据库连接池
 */

public class C3P0Test {


    // 连接方式一：不推荐
    @Test
    public void testGetConnection1() throws Exception{
        // 获取 c3p0 数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
        cpds.setUser("parzulpan");
        cpds.setPassword("parzulpan");

        //
        cpds.setInitialPoolSize(10);
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }

    // 连接方式二：使用配置文件，推荐
    // 使用时，需要将 cpds 声明为 static，然后用 static 代码块为 cpds 赋值
    @Test
    public void testGetConnection2() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

        Connection connection = cpds.getConnection();
        System.out.println(connection);

        // 销毁 c3p0 数据库连接池，慎用
//        DataSources.destroy(cpds);
    }
}

```

```xml
<!-- c3p0配置文件-->
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
    <named-config name="helloc3p0">
        <!-- 获取连接的基本信息-->
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/test?useSSL=false</property>
        <property name="user">parzulpan</property>
        <property name="password">parzulpan</property>

        <!-- 数据库连接池的管理的相关属性的设置-->
        <!-- 若数据库连接池中连接数不足时，一次向数据库服务器申请的连接数-->
        <property name="acquireIncrement">5</property>
        <!-- 初始化数据库连接池时连接的数量-->
        <property name="initialPoolSize">5</property>
        <!-- 数据库连接池中的最小的数据库连接数-->
        <property name="minPoolSize">5</property>
        <!-- 数据库连接池中的最大的数据库连接数-->
        <property name="maxPoolSize">10</property>
        <!-- 数据库连接池可以维护的 Statement 的个数 -->
        <property name="maxStatements">20</property>
        <!-- 每个连接同时可以使用的 Statement 对象的个数 -->
        <property name="maxStatementsPerConnection">5</property>
    </named-config>
</c3p0-config>
```

---

```java
package cn.parzulpan.jdbc.ch08;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : dbcp 数据库连接池
 */

public class DBCPTest {

    // 连接方式一：不推荐
    @Test
    public void testGetConnection1() throws Exception{
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
        bds.setUsername("parzulpan");
        bds.setPassword("parzulpan");

        bds.setInitialSize(10);

        Connection connection = bds.getConnection();
        System.out.println(connection);
    }

    // 连接方式二：使用配置文件，推荐
    // 使用时，需要将 dataSource 声明为 static，然后用 static 代码块为 dataSource 赋值
    @Test
    public void testGetConnection2() throws Exception{
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        properties.load(is);

        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
```

```properties
# dbcp 配置文件
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?useSSL=false
username=parzulpan
password=parzulpan

initialSize=10
```

---

```java
package cn.parzulpan.jdbc.ch08;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : Druid 数据库连接池
 */

public class DruidTest {

    // 连接方式一：使用配置文件，推荐
    // 使用时，需要将 dataSource 声明为 static，然后用 static 代码块为 dataSource 赋值
    @Test
    public void testGetConnection1() throws Exception{
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        properties.load(is);

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
```

```properties
# Druid 配置文件
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?useSSL=false
username=parzulpan
password=parzulpan

initialSize=10
maxActive=20
maxWait=1000
filters=wall
```

## 练习和总结
