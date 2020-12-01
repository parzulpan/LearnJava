package cn.parzulpan.jdbc.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
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
    private static DataSource dataSource = null;

    static {
        try {
            Properties properties = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
     * 获取数据库连接，使用 Druid 数据库连接池
     * @return 数据库连接
     */
    public static Connection getDruidConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 使用DbUtils，静默关闭数据库资源
     * @param connection 数据库连接
     * @param statement 声明
     * @param resultSet 结果集
     */
    public static void closeResourceQuietly(Connection connection, Statement statement, ResultSet resultSet) {
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(resultSet);
    }

    /**
     * 关闭数据库资源
     * @param connection 数据库连接
     * @param statement 声明
     */
    public static void closeResource(Connection connection, Statement statement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库资源
     * @param connection 数据库连接
     * @param statement 声明
     * @param resultSet 结果集
     */
    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 PreparedStatement 实现增、删、改操作
     * @param sql sql 语句
     * @param args  占位符
     * @return 是否有数据更新
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
            closeResource(connection, ps);
        }

        return 0;
    }

    /**
     * 使用事务实现增、删、改操作
     * @param connection 数据库连接
     * @param sql sql 语句
     * @param args 占位符
     * @return 是否有数据更新
     */
    public static int update(Connection connection, String sql, Object ...args) {
        PreparedStatement ps = null;
        try {
            if (connection != null) {
                ps = connection.prepareStatement(sql);
            }

            if (ps != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }

                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource(null, ps);
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
            closeResource(connection, ps, rs);
        }

        return null;
    }

    /**
     * 使用事务
     * @param clazz 类对象
     * @param sql sql 语句
     * @param args  占位符
     * @param <T> 泛型
     * @return 返回一个对象
     */
    public <T> T getQuery(Connection connection, Class<T> clazz, String sql, Object ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData md = rs.getMetaData();

            int columnCount = md.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) { // 遍历每一行
                    String columnLabel = md.getColumnLabel(i + 1);
                    Object columnVal = rs.getObject(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }

                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(null, ps, rs);
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
            closeResource(connection, ps, rs);
        }

        return null;
    }

}
