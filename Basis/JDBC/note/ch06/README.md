# 数据库事务

## 概述

事务是逻辑上的一组操作，或者说一个独立的工作单元。事务内的语句，要么全部执行成功，要么全部执行失败。

## 事务处理

**数据一旦提交，就不可回滚**。数据意味着提交的情况：

* 当一个连接对象被创建时，默认情况下时自动提交事务：
  * DDL 操作一旦执行成功，就会自动提交。`connection.setAutoCommit(false);` 对 DDL 无效。
  * DML 操作默认情况下一旦执行成功，就会自动提交。`connection.setAutoCommit(false);` 对 DML 取消自动提交。
* 当一个连接被关闭时，事务就会自动的提交：
  * 如果有多个操作，每个操作使用的是自己单独的连接，则无法保证事务，所以同一个事务的多个操作必须在同一个连接下。

JDBC 中让多条 SQL 语句作为一个事务执行：

* 使用 `connection.setAutoCommit(false);` ，取消自动提交事务；
* 在所有 SQL 语句都成功执行后，调用 `connection.commit();`，提交事务；
* 在出现异常是，调用 `connection.rollback();`，回滚事务。

**注意**：若此时 Connection 没有被关闭，还可能被重复使用，则需要恢复其自动提交状态 `connection.setAutoCommit(true);`。尤其是在使用数据库连接池技术时，执行 `close()` 方法前，建议恢复自动提交状态。

```java
package cn.parzulpan.jdbc.ch06;

import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 事务问题，AA 用户 向 BB 用户转账 100
 */

public class TransactionTest {

    // 出现事务问题
    @Test
    public void test1() {
        String sql1 = "update user_table set balance = balance - 100 where user = ?";
        JDBCUtils.update(sql1, "AA");

        // 模拟网络异常
        System.out.println(10 / 0);

        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        JDBCUtils.update(sql2, "BB");
    }

    // 考虑事务问题
    @Test
    public void test2() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            // 取消事务自动提交
            connection.setAutoCommit(false);

            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            JDBCUtils.update(connection, sql1, "AA");

            // 模拟网络异常
            System.out.println(10 / 0);

            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            JDBCUtils.update(connection, sql2, "BB");

            // 若没有异常，则提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // 如有异常，则回滚事务
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                // 恢复每次 DML 操作的自动提交功能，针对于数据库连接池时
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 关闭连接
            JDBCUtils.closeResource(connection, null, null);
        }
    }
}
```

## 事务的特性

**事务的 ACID 属性**：

* 原子性（Atomicity）：指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。
* 一致性（Consistency）：指数据库总是从一个一致性状态切换到另一个一致性状态。
* 隔离性（Isolation）：指并发执行的一个事务之间不能互相干扰；
* 持久性（Durability）：指事务一旦提交，它对数据库的改变是永久性的。

**数据库的并发问题**：

* 对于同时运行的多个事务，当这些事务访问数据库的相同数据时，如果没有采取必要的**隔离机制**，就会导致各种并发问题：
  * **脏读**：对于事务 T1 和 T2，T1 读取了已经被 T2 **更新但还没提交** 的数据，若 T2 回滚，T1 读取的内容就是临时且无效的。
  * **不可重复读**：对于事务 T1 和 T2，T1 读取了一个数据，然后 T2 更新了这个数据，之后 T1 再次读取这个数据，数据值改变。
  * **幻读**：对于事务 T1 和 T2，T1 读取了表的数据，然后 T2 向表增加了一些数据，之后 T1 再次读取这个表，表就会多处几行。
* 数据库系统必须具有隔离并发运行各个事务的能力，使它们不会相互影响，避免各种并发问题，这就是数据库事务的隔离性。

隔离级别越高，数据的一致性就越好，但并发行就越弱。

**四种事务隔离级别**：

* `TRANSACTION_READ_UNCOMMITTED` 读未提交：允许事务读取未被其他事务提交的变更，出现 脏读、不可重复读、幻读等问题。
* `TRANSACTION_READ_COMMITTED` 读已提交：只允许事务读取已经被其他事务提交的变更，避免 脏读问题，出现 不可重复读、幻读等问题。这是 Oracle 默认的事务隔离级别
* `TRANSACTION_REPEATABLE_READ` 可重复读：确保在同一个事务中多次读取同样记录的结果是一致的，避免 脏读、不可重复读问题，出现 幻读等问题。这是 MySQL 默认的事务隔离级别。
* `TRANSACTION_SERIALIZABLE` 串行化：确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表进行增删改操作，避免上面所有问题。

**设置事务隔离级别**：

* 每个数据库连接都有一个全局变量 `@@tx_isolation`，表示当前的事务隔离级别。
* 查看当前的事务隔离级别：
  
  ```sql
  select @@tx_isolation;
  ```

* 设置当前数据库连接的隔离级别：

  ```sql
  set transaction isolation level read committed;
  ```

* 设置数据库全局的隔离级别：

  ```sql
  set global transaction isolation level read committed;
  ```

* 创建 MySQL 用户，并授予权限：

  ```sql
  create user parzulpan identified by 'xxx';

  # 授予网络方式登陆的用户，对所有库所有表的全部权限，并设置密码
  grant all privileges on *.* to parzulpan@'%' identified by 'xxx';

  # 授予本地方式登陆的用户，对 test 库所有表的增删改查权限，并设置密码
  grant select, insert, delete, update on test.* to parzulpan@localhost identified by 'xxx';
  ```



```java
package cn.parzulpan.jdbc.ch06;

import cn.parzulpan.jdbc.bean.UserTable;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 事务隔离级别
 */

public class TransactionTest1 {

    // 模拟 事务隔离级别
    @Test
    public void test1() throws Exception{
        Connection connection = JDBCUtils.getConnection();

        // TRANSACTION_READ_UNCOMMITTED 读未提交数据
        // 允许事务读取未被其他事务提交的变更，出现 脏读、不可重复读、幻读等问题
        // 先运行 test2()，然后运行 test1()，发现数据已更改，但是10秒后，再然后运行 test1()，发现数据已恢复
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        // TRANSACTION_READ_COMMITTED 读已提交数据
        // 只允许事务读取已经被其他事务提交的变更，避免 脏读问题，出现 不可重复读、幻读等问题
        // 先运行 test2()，然后运行 test1()，发现数据未更改，10秒后，再然后运行 test1()，发现数据依然未更改
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        // TRANSACTION_REPEATABLE_READ 可重复读
        // 确保在同一个事务中多次读取同样记录的结果是一致的，避免 脏读、不可重复读问题，出现 幻读等问题
        // 打开 test2() 自动事务提交，先运行 test2()，然后运行 test1()，发现数据未更改，10秒后，再然后运行 test1()，发现数据依然未更改
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);


        System.out.println(connection.getTransactionIsolation());

        connection.setAutoCommit(false);

        String sql = "select user, password, balance from user_table where user = ?";
        JDBCUtils jdbcUtils = new JDBCUtils();
        UserTable userTable = jdbcUtils.getQuery(connection, UserTable.class, sql, "CC");
        System.out.println(userTable);
    }

    // 模拟 事务隔离级别
    @Test
    public void test2() throws Exception{
        Connection connection = JDBCUtils.getConnection();

        System.out.println(connection.getTransactionIsolation());

        connection.setAutoCommit(false);

        String sql = "update user_table set balance = ? where user = ?";
        JDBCUtils jdbcUtils = new JDBCUtils();
        jdbcUtils.update(connection, sql, 5000, "CC");

        Thread.sleep(5000);
        System.out.println("修改结束"); // 注意此时事务并未提交
    }
}
````

## 练习和总结
