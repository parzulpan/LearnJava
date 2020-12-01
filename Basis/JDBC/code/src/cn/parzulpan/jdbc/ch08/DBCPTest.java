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
