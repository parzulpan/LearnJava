package cn.parzulpan.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-12-08
 * @Desc : 操作数据库的工具类
 */

public class JDBCUtils {
    private static DataSource dataSource = null;

    static {
        try {
            Properties properties = new Properties();
//            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接，使用 Druid 数据库连接池
     * @return 数据库连接
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 使用DbUtils，静默关闭数据库资源
     * @param connection 数据库连接
     * @param statement 声明
     * @param resultSet 结果集
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(resultSet);
    }

    /**
     * 获取 SQL 格式的日期
     * @param dateStr 日期字符串
     * @return SQL 格式的日期
     */
    public static Date getSqlDate(String dateStr) {
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
