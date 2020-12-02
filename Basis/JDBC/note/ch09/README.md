# commons-dbutils

## 简介

commons-dbutils 是 Apache 组织提供的一个开源 JDBC 工具类库，它是对 JDBC 的简单封装，学习成本极低，并且使用 commons-dbutils 能极大简化 JDBC 编码的工作量，同时也不会影响程序的性能。

主要类：

* **QueryRunner**：提供数据库操作的一些重载的 `update()` 和 `query()` 方法；
* **ResultSetHandler**：此接口用于处理数据库查询结果得到的结果集，不同的结果集有不同的子类实现；
* **DbUtils**：提供如关闭连接、装载 JDBC 驱动程序等常规工作的工具类，里面的所有方法都是静态的。

## 使用

**QueryRunner 类**：

* 该类简化了 SQL 查询，它与 ResultSetHandler 组合在一起使用可以完成大部分的数据库操作，能够大大减少编码量**。
* 该类提供了两个构造器：
  * 默认的构造器；
  * 需要一个 `javax.sql.DataSource` 来作参数的构造器；
* 主要方法：
  * **更新**
    * `public int update(Connection conn, String sql, Object... params) throws SQLException` 用来执行一个更新（插入、更新或删除）操作。
  * **插入**
    * `public <T> T insert(Connection conn,String sql,ResultSetHandler<T> rsh, Object... params) throws SQLException` 只支持插入操作。
  * **批处理**
    * `public int[] batch(Connection conn,String sql,Object[][] params)throws SQLException` 插入、更新或删除操作。
    * `public <T> T insertBatch(Connection conn,String sql,ResultSetHandler<T> rsh,Object[][] params)throws SQLException` 只支持插入操作。
  * **查询**
    * `public Object query(Connection conn, String sql, ResultSetHandler rsh,Object... params) throws SQLException` 执行一个查询操作，在这个查询中，对象数组中的每个元素值被用来作为查询语句的置换参数。该方法会自行处理 PreparedStatement 和 ResultSet 的创建和关闭。

**ResultSetHandler 接口**：

* 该接口用于处理 `java.sql.ResultSet`，将数据按要求转换为另一种形式；
* 主要实现类：
  * `ArrayHandler` 把结果集中的第一行数据转成对象数组。
  * `ArrayListHandler` 把结果集中的每一行数据都转成一个数组，再存放到 List 中。
  * **`BeanHandler`** 将结果集中的第一行数据封装到一个对应的  JavaBean 实例中。
  * **`BeanListHandler`** 将结果集中的每一行数据都封装到一个对应的 JavaBean 实例中，存放到List里。
  * `ColumnListHandler` 将结果集中某一列的数据存放到 List 中。
  * `KeyedHandler(name)`：将结果集中的每一行数据都封装到一个 Map 里，再把这些 map 再存到一个 map 里，其 key 为指定的 key。
  * **`MapHandler`** 将结果集中的第一行数据封装到一个 Map 里，key 是列名，value 就是对应的值。
  * **`MapListHandler`** 将结果集中的每一行数据都封装到一个 Map里，然后再存放到 List。
  * **`ScalarHandler`** 查询单个值对象。

```java
package cn.parzulpan.jdbc.ch09;

import cn.parzulpan.jdbc.bean.Customer;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : DBUtils QueryRunner 使用
 */

public class QueryRunnerTest {

    // 插入记录
    @Test
    public void testInsert() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int update = queryRunner.update(connection, sql, "劲儿", "jiner@163.com", "1995-12-29");
            System.out.println("添加了 " + update + " 条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 删除记录
    @Test
    public void testDelete() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "delete from customers where id < ?";
            int update = queryRunner.update(connection, sql, 3);
            System.out.println("删除了 " + update + " 条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 修改记录
    @Test
    public void testUpdate() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "update customers set email = ? where name = ?";
            int update = queryRunner.update(connection, sql, "jinerR@163.com", "劲儿");
            System.out.println("修改了 " + update + " 条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询一条记录，BeanHandler
    @Test
    public void testQuery() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id = ?";
            // BeanHandler 是 ResultSetHandler 的实现类，用于封装表中的一条记录
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = queryRunner.query(connection, sql, handler, 23);
            System.out.println(customer);   // Customer{id=23, name='劲儿', email='jinerR@163.com', birth=1995-12-29}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询一条记录，MapHandler
    @Test
    public void testQueryMap() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id = ?";
            MapHandler mapHandler = new MapHandler();
            Map<String, Object> map = queryRunner.query(connection, sql, mapHandler, 23);
            System.out.println(map);    // {name=劲儿, birth=1995-12-29, id=23, email=jinerR@163.com}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询多条记录，BeanListHandler
    @Test
    public void testQueryList() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id < ?";
            BeanListHandler<Customer> listHandler = new BeanListHandler<>(Customer.class);
            List<Customer> customerList = queryRunner.query(connection, sql, listHandler, 13);
            customerList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询多条记录，MapListHandler
    @Test
    public void testQueryMapList() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id < ?";
            MapListHandler mapListHandler = new MapListHandler();
            List<Map<String, Object>> mapList = queryRunner.query(connection, sql, mapListHandler, 13);
            mapList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    // 查询特殊值，类似于最大的，最小的，平均的，总和，个数相关的数据，ScalarHandler
    @Test
    public void testQueryScalar() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();

            QueryRunner queryRunner = new QueryRunner();

            String sql = "select count(*) from customers where id < ?";
            ScalarHandler scalarHandler = new ScalarHandler();
            Object query = queryRunner.query(connection, sql, scalarHandler, 20);
            System.out.println(query);

            String sql1 = "select max(birth) from customers";
            ScalarHandler handler = new ScalarHandler();
            Object query1 = queryRunner.query(connection, sql1, handler);
            System.out.println(query1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }


}
```

## 练习和总结
