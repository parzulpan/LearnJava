# 实现 CRUD 操作

## 操作和访问数据库

数据库连接被用于向数据库服务器发送命令和 SQL 语句，并接受数据库服务器返回的结果。其实一个数据库连接就是一个 Socket 连接。

java.sql 包中有三个接口分别定义了对数据库的调用的不同方式：

* Statement：用于执行静态 SQL 语句并返回它所生成结果的对象；
* PreparedStatement：SQL 语句被预编译并存储在此对象中，可以使用此对象多次高效地执行该语句；
* 用于执行 SQL 存储过程；

## 使用 Statement

通过调用 Connection 对象的 `createStatement()` 方法创建该对象。该对象用于执行静态的 SQL 语句，并且返回执行结果。

Statement 接口中定义了下列方法用于执行 SQL 语句：

* `int excuteUpdate(String sql)`：执行更新操作 INSERT、UPDATE、DELETE；
* `ResultSet executeQuery(String sql)`：执行查询操作 SELECT；

使用 Statement 操作数据表的存在弊端：

* **问题一**：存在拼串操作，繁琐；
* **问题二**：由拼串操作可能引发 **SQL 注入**问题。
* **SQL 注入**是利用某些系统没有对用户输入的数据进行充分的检查，而在用户输入数据中注入非法的 SQL 语句段或命令，如：`SELECT user, password FROM user_table WHERE user='a' OR 1 = ' AND password = ' OR '1' = '1'`，从而利用系统的 SQL 引擎完成恶意行为的做法。

## 使用 PreparedStatement

* 可以通过调用 Connection 对象的 `preparedStatement(String sql)` 方法获取 PreparedStatement 对象；
* PreparedStatement 接口是 Statement 的子接口，它表示一条预编译过的 SQL 语句；
* PreparedStatement 对象所代表的 SQL 语句中的参数用问号 `(?)` 来表示，调用 PreparedStatement 对象的 `setXxx()` 方法来设置这些参数. `setXxx()` 方法有两个参数：
  * 第一个参数是要设置的 SQL 语句中的参数的索引，注意是**从 1 开始**；
  * 第二个是设置的 SQL 语句中的参数的值；

相比于 Statement，PreparedStatement 有如下优势：

* 代码的可读性和可维护性更好；
* 能最大可能的提高性能（预编译、缓冲等）；
* 可以防止 SQL 注入；
* 可以操作 BLOB 类型字段。

**Java 与 SQL 对应数据类型转换表**：

| Java类型           | SQL类型                  |
| ------------------ | ------------------------ |
| boolean            | BIT                      |
| byte               | TINYINT                  |
| short              | SMALLINT                 |
| int                | INTEGER                  |
| long               | BIGINT                   |
| String             | CHAR, VARCHAR, LONGVARCHAR |
| byte, array        | BINARY, VAR BINARY       |
| java.sql.Date      | DATE                     |
| java.sql.Time      | TIME                     |
| java.sql.Timestamp | TIMESTAMP                |

**ResultSet**：

* 查询需要调用 PreparedStatement 的 `executeQuery(`) 方法，查询结果是一个 `ResultSet` 对象；
* ResultSet 对象以逻辑表格的形式封装了执行数据库操作的结果集，ResultSet 接口由数据库厂商提供实现；
* ResultSet 返回的实际上就是一张**数据表**，有一个指针指向数据表的第一条记录的前面；
* ResultSet 对象维护了一个指向当前数据行的**游标**，初始的时候，游标在第一行之前，可以通过 ResultSet 对象的 `next()` 方法移动到下一行。调用 next() 方法检测下一行是否有效。若有效，该方法返回 true，且指针下移。相当于 Iterator对象的 hasNext() 和 next() 方法的结合体；
* 当指针指向一行时, 可以通过调用 `getXxx(int index)` 或 `getXxx(int columnName)` 获取每一列的值：
  * 例如: `getInt(1)`, `getString("name")`；
  * **注意：Java 与数据库交互涉及到的相关 Java API 中的索引都从 1 开始。**
* ResultSet 接口的常用方法：
  * `boolean next()`；
  * `getString()`；

**ResultSetMetaData**：

* 它可用于获取关于 ResultSet 对象中列的类型和属性信息的对象；
* ResultSetMetaData meta = rs.getMetaData();
  * **`getColumnName(int column)`**：获取指定列的名称
  * **`getColumnLabel(int column)`**：获取指定列的别名
  * **`getColumnCount()`**：返回当前 ResultSet 对象中的列数
  * `getColumnTypeName(int column)`：检索指定列的数据库特定的类型名称
  * `getColumnDisplaySize(int column)`：指示指定列的最大标准宽度，以字符为单位
  * **`isNullable(int column)`**：指示指定列中的值是否可以为 null
  * `isAutoIncrement(int column)`：指示是否自动为指定列进行编号，这样这些列仍然是只读的

**资源的释放**：

* 释放 ResultSet, Statement, Connection；
* 数据库连接（Connection）是非常稀有的资源，用完后必须马上释放，如果 Connection 不能及时正确的关闭将导致系统宕机。Connection 的使用原则是**尽量晚创建，尽量早的释放；**
* 可以在 finally 中关闭，保证及时其他代码出现异常，资源也一定能被关闭。

**ORM（Object relational mapping） 思想**：

* 一个数据表对应一个 Java 类；
* 表中的一条记录对应 Java 类的一个对象；
* 表中的一个字段对应 Java 类的一个属性；
* 结合 列名 和 表的属性名，注意别名问题。

**获取对象**：

* 熟练使用泛型和反射机制；
* 通过反射创建指定类的对象，获取指定属性并赋值。

```java
package cn.parzulpan.jdbc.bean;

import java.sql.Date;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : ORM 编程思想，对应 customers 表
 * 一个数据表对应一个 Java 类
 * 表中的一条记录对应 Java 类的一个对象
 * 表中的一个字段对应 Java 类的一个属性
 */


public class Customer {
    private int id;
    private String name;
    private String email;
    private Date birth;

    public Customer() {
    }

    public Customer(int id, String name, String email, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date date) {
        this.birth = date;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}
```

```java
package cn.parzulpan.jdbc.bean;

import java.sql.Date;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : ORM 编程思想，对应 order 表，注意别名问题
 */

public class Order {
    private int orderId;
    private String orderName;
    private Date orderDate;

    public Order() {
    }

    public Order(int orderId, String orderName, Date orderDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
```

```java
package cn.parzulpan.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : 操作数据库的工具类
 */

public class JDBCUtils {

    /**
     * 获取数据库连接
     * @return 数据库连接
     */
    public static Connection getConnection() {
        try {
            // 1. 加载配置信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(is);

            // 2. 读取配置信息
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverName = properties.getProperty("driverName");

            // 3. 加载驱动
            Class.forName(driverName);

            // 4. 获取连接
            return DriverManager.getConnection(url, user, password);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 关闭数据库资源
     * @param connection 数据库连接
     * @param statement 声明
     */
    public static void closeResource(Connection connection, Statement statement) {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库资源
     * @param connection 数据库连接
     * @param statement 声明
     * @param rs 结果集
     */
    public static void closeResource(Connection connection, Statement statement, ResultSet rs) {
        try {
            connection.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 PreparedStatement 实现增、删、改操作
     * @param sql sql 语句
     * @param args  占位符
     */
    public static int update(String sql, Object ...args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            // 获取数据库连接
            connection = getConnection();

            // 预编译 sql 语句，获取 PreparedStatement 实例
            if (connection != null) {
                ps = connection.prepareStatement(sql);
            }

            if (ps != null) {
                // 填充占位符
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }

                // 执行 sql 语句，如果是查询操作且有返回结果，返回 true；如果是增删改操作且五无返回结果，返回 false；
//                ps.execute();
                // 有返回值，返回受影响的行数
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null && ps != null) {
                closeResource(connection, ps);
            }
        }

        return 0;
    }

    /**
     * 使用 PreparedStatement 实现不同表的查操作，并返回表中的一条记录
     * @param clazz 类对象
     * @param sql sql 语句
     * @param args  占位符
     * @param <T> 泛型
     * @return 返回一个对象
     */
    public <T> T getQuery(Class<T> clazz, String sql, Object ...args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            connection = getConnection();

            // 预编译 sql 语句，获取 PreparedStatement 实例
            if (connection != null) {
                ps = connection.prepareStatement(sql);
            }

            if (ps != null) {
                // 填充占位符
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }

                // 执行 sql 语句，得到结果集
                rs = ps.executeQuery();

                //得到结果集的元数据
                ResultSetMetaData md = rs.getMetaData();

                // 通过结果集的元数据得到 列数 columnCount、列的别名 columnLabel
                int columnCount = md.getColumnCount();
                if (rs.next()) {    // 判断结果集下一条是否有数据(即下一行是否有效)，如果有数据，则返回 true 并指针下移
                    T t = clazz.newInstance();
                    for (int i = 0; i < columnCount; i++) { // 遍历每一行
                        // 获取列名
//                        String columnLabel = md.getColumnName(i + 1);
                        // 获取列的别名，为了解决对象属性名和列名不一致，所以将列名的别名作为对象属性名
                        String columnLabel = md.getColumnLabel(i + 1);
                        // 获取列值
                        Object columnVal = rs.getObject(i + 1);

//                        System.out.println(columnLabel + " : " + columnVal);

                        // 使用反射，给对象的相应属性赋值
                        Field field = clazz.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(t, columnVal);
                    }

                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null && ps != null && rs != null) {
                closeResource(connection, ps, rs);
            }
        }

        return null;
    }

    /**
     * 使用 PreparedStatement 实现不同表的查操作，并返回表中的所有记录
     * @param clazz 类对象
     * @param sql sql 语句
     * @param args  占位符
     * @param <T> 泛型
     * @return 返回一个对象集合
     */
    public <T> List<T> getAllQuery(Class<T> clazz, String sql, Object ...args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            connection = getConnection();

            // 预编译 sql 语句，获取 PreparedStatement 实例，这是防止 SQL 注入的关键
            if (connection != null) {
                ps = connection.prepareStatement(sql);
            }

            if (ps != null) {
                // 填充占位符
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }

                // 执行 sql 语句，得到结果集
                rs = ps.executeQuery();

                //得到结果集的元数据
                ResultSetMetaData md = rs.getMetaData();

                // 通过结果集的元数据得到 列数 columnCount、列名 columnLabel
                int columnCount = md.getColumnCount();

                // 创建集合对象
                ArrayList<T> list = new ArrayList<>();
                while (rs.next()) {    // 判断结果集下一条是否有数据，如果有数据，则返回 true 并指针下移
                    T t = clazz.newInstance();
                    for (int i = 0; i < columnCount; i++) { // 遍历每一个行
                        // 获取列名
//                        String columnLabel = md.getColumnName(i + 1);
                        // 获取列的别名，为了解决对象属性名和列名不一致，所以将对象属性名作为列名的别名
                        String columnLabel = md.getColumnLabel(i + 1);
                        // 获取列值
                        Object columnVal = rs.getObject(i + 1);

//                        System.out.println(columnLabel + " : " + columnVal);

                        // 使用反射，给对象的相应属性赋值
                        Field field = clazz.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(t, columnVal);
                    }

                   list.add(t);
                }

                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null && ps != null && rs != null) {
                closeResource(connection, ps, rs);
            }
        }

        return null;
    }

}
```

```java
package cn.parzulpan.jdbc.ch03;

import cn.parzulpan.jdbc.bean.Customer;
import cn.parzulpan.jdbc.bean.Order;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : PreparedStatement 的使用
 */

public class PreparedStatementTest {

    // 向 customers 表中添加一条记录
    @Test
    public void testInsert() {
        String sql = "insert into customers(name, email, birth)values(?,?,?)";
        String name = "咖喱";
        String email = "curry@gmail.com";
        java.sql.Date date = null;
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("1999-05-05");
            date = new java.sql.Date(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JDBCUtils.update(sql, name, email, date);
    }

    // 从 customers 表中删除一条记录
    @Test
    public void testDelete() {
        String sql = "delete from customers where name = ?";
        String name = "咖喱";
        JDBCUtils.update(sql, name);
    }

    // 向 customers 表中更新一条记录
    @Test
    public void testUpdate() {
        String sql = "update customers set email = ? where name = ?";
        String name = "咖喱";
        String email = "currySuper@gmail.com";
        JDBCUtils.update(sql, email, name);
    }

    // 从 customers 和 order 表中查询一条记录
    @Test
    public void testSelect() {
        String sql = "select id, name, email, birth from customers where name = ?";
        String name = "咖喱";
        JDBCUtils jdbcUtils = new JDBCUtils();
        Customer customer = jdbcUtils.getQuery(Customer.class, sql, name);
        System.out.println(customer);

        System.out.println();

        // 注意别名问题和表名是关键字的问题（``）
        String sql1 = "select order_id orderId, order_name orderName, order_date orderDate from `order` " +
                "where order_id = ?";
        int orderId = 4;
        Order order = jdbcUtils.getQuery(Order.class, sql1, orderId);
        System.out.println(order);
    }

    // 从 customers 和 order 表中查询多条记录
    @Test
    public void testAllSelect() {
        JDBCUtils jdbcUtils = new JDBCUtils();
        String sql2 = "select id, name, email, birth from customers where id < ?";
        int id = 10;
        List<Customer> customers = jdbcUtils.getAllQuery(Customer.class, sql2, id);
        customers.forEach(System.out::println);

        System.out.println();

        String sql1 = "select order_id orderId, order_name orderName, order_date orderDate from `order` " +
                "where order_id < ?";
        int orderId = 4;
        List<Order> orders = jdbcUtils.getAllQuery(Order.class, sql1, orderId);
        orders.stream().forEach(System.out::println);
    }
}
```

## 练习和总结

---

**从控制台向数据库的表 customers 中插入一条数据？**

```java
package cn.parzulpan.jdbc.exer;

import cn.parzulpan.jdbc.util.JDBCUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : 练习1：从控制台向数据库的表 customers 中插入一条数据
 */

public class Test1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter name: ");
        String name = scanner.next();
        System.out.println("enter email: ");
        String email = scanner.next();
        System.out.println("enter birth");
        String date = scanner.next();
        java.sql.Date birth = null;
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            birth = new java.sql.Date(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql = "insert into customers(name, email, birth)values(?,?,?)";

        int update = JDBCUtils.update(sql, name, email, birth);
        if (update > 0) {
            System.out.println("插入成功!");
        } else {
            System.out.println("插入失败!");
        }
    }
}
```

---

**创建数据库表 examstudent，插入一个新的 student 信息，输入身份证号或准考证号可以查询和删除学生的基本信息？**

```java
package cn.parzulpan.jdbc.exer;

import cn.parzulpan.jdbc.bean.ExamStudent;
import cn.parzulpan.jdbc.util.JDBCUtils;

import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : 练习2：创建数据库表 examstudent，插入一个新的student 信息，输入身份证号或准考证号可以查询和删除学生的基本信息
 */

public class Test2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Test2 test2 = new Test2();
        while (true) {
            System.out.print("请选择操作类型（a: 添加、b: 查询、c: 删除、q: 退出）: ");
            String selection = scanner.next();
            if ("a".equalsIgnoreCase(selection)) {
                test2.insert();
            } else if ("b".equalsIgnoreCase(selection)) {
                test2.query();
            } else if ("c".equalsIgnoreCase(selection)) {
                test2.delete();
            } else if ("q".equalsIgnoreCase(selection)) {
                break;
            } else {
                System.out.println("您的输入有误！请重新输入！");
            }
        }
    }

    public void insert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type: ");
        int type = scanner.nextInt();
        System.out.print("IDCard: ");
        String idCard = scanner.next();
        System.out.print("ExamCard: ");
        String examCard = scanner.next();
        System.out.print("StudentName: ");
        String studentName = scanner.next();
        System.out.print("Location: ");
        String location = scanner.next();
        System.out.print("Grade: ");
        int grade = scanner.nextInt();

        String sql = "insert into examstudent(Type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
        int update = JDBCUtils.update(sql, type, idCard, examCard, studentName, location, grade);
        if (update > 0) {
            System.out.println("信息录入成功！");
        } else {
            System.out.println("信息录入失败！");
        }
    }

    public void query() {
        JDBCUtils jdbcUtils = new JDBCUtils();
        Scanner scanner = new Scanner(System.in);
        System.out.print("请选择您要输入的类型（a: 准考证号、b: 身份证号）: ");
        String selection = scanner.next();
        if ("a".equalsIgnoreCase(selection)) {
            System.out.print("请输入准考证号: ");
            String examCard = scanner.next();
            String sql = "select FlowID flowID, Type type, IDCard idCard, ExamCard examCard," +
                    "StudentName studentName, Location location, Grade grade from examstudent where ExamCard = ?";
            ExamStudent examStudent = jdbcUtils.getQuery(ExamStudent.class, sql, examCard);
            if (examStudent != null) {
                System.out.println(examStudent);
            } else {
                System.out.println("查无此人！请重新输入！");
            }

        } else if ("b".equalsIgnoreCase(selection)) {
            System.out.print("请输入身份证号: ");
            String idCard = scanner.next();
            String sql = "select FlowID flowID, Type type, IDCard idCard, ExamCard examCard," +
                    "StudentName studentName, Location location, Grade grade from examstudent where IDCard = ?";
            ExamStudent examStudent = jdbcUtils.getQuery(ExamStudent.class, sql, idCard);
            if (examStudent != null) {
                System.out.println(examStudent);
            } else {
                System.out.println("查无此人！请重新输入！");
            }
        } else {
            System.out.println("您的输入有误！请重新输入！");
        }
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入的学生的准考证号: ");
        String examCard = scanner.next();
        String sql = "delete from examstudent where ExamCard = ?";
        int update = JDBCUtils.update(sql, examCard);
        if (update > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("查无此人！请重新输入！");
        }
    }

}
```

---
