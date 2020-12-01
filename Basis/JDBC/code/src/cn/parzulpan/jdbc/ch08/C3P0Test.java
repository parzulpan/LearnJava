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
