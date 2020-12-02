# JDBC

## 目录

* [JDBC 概述](note/ch01/README.md)
* [获取数据库连接](note/ch02/README.md)
* [实现 CRUD 操作](note/ch03/README.md)
* [操作 BLOB 类型字段](note/ch04/README.md)
* [批量插入](note/ch05/README.md)
* [数据库事务](note/ch06/README.md)
* [数据库连接池](note/ch08/README.md)
* [commons-dbutils](note/ch09/README.md)
* [DAO 相关](note/ch07/README.md)

## 总结

```java
// JDBC 总结
public void JDBC() {
    Connection connection = null;
    try {
        // 1. 获取数据库连接
        // 1.1 手写的连接（JDBCUtils）：加载配置信息 -> 读取配置信息 -> 加载驱动 -> 获取连接
        // 1.2 数据库连接池：C3P0、DBCP、Druid

        // 取消事务自动提交
        connection.setAutoCommit(false);

        // 2. 对数据表进行一系列 CRUD 操作
        // 2.1 使用 PreparedStatement 实现通用的增删改、查询操作
        // 2.2 考虑事务实现通用的增删改、查询操作
        // 2.3 使用 commons-dbutils

        // 提交数据
        connection.commit();
    } catch (Exception e) {
        e.printStackTrace();

        try {
            // 回滚数据
            connection.rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    } finally {
        // 恢复事务自动提交
        connection.setAutoCommit(true);

        // 3. 关闭数据库连接
        // 3.1 手写的关闭（JDBCUtils）
        // 3.2 使用 commons-dbutils
    }
}
```

## 说明
