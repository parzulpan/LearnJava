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
