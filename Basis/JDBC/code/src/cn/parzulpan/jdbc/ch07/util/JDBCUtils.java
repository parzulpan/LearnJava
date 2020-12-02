package cn.parzulpan.jdbc.ch07.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @Author : parzulpan
 * @Time : 2020-12-02
 * @Desc : 操作数据库的工具类，最终版
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

    public static Date getSqlDate(String dateStr) {
        java.sql.Date date = null;
        try {
            java.util.Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            date = new java.sql.Date(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
