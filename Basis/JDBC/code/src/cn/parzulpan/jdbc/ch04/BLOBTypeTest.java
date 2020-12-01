package cn.parzulpan.jdbc.ch04;

import cn.parzulpan.jdbc.bean.Customer;
import cn.parzulpan.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 操作 BLOB 类型字段
 */

public class BLOBTypeTest {

    // 向数据表中插入大数据类型
    @Test
    public void insertBLOBType() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers(name, email, birth, photo)values(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, "猫猫");
        ps.setString(2, "cat@gmail.com");
        ps.setDate(3, new Date(new java.util.Date().getTime()));

        //
        FileInputStream fis = new FileInputStream(new File("cat.png"));
        ps.setBlob(4, fis);

        ps.execute();

        fis.close();
        JDBCUtils.closeResource(connection, ps);
    }

    // 修改数据表中的大数据类型
    @Test
    public void updateBLOBType() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        String sql = "update customers set photo = ? where name = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        // 批量插入
        FileInputStream fis = new FileInputStream(new File("girl.jpg"));
        ps.setBlob(1, fis);
        ps.setString(2, "猫猫");

        ps.execute();

        fis.close();
        JDBCUtils.closeResource(connection, ps);
    }

    // 从数据表中读取大数据类型
    @Test
    public void queryBLOBType() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        String sql = "select id, name, email, birth, photo from customers where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, 22);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            Date birth = rs.getDate("birth");
            Customer customer = new Customer(id, name, email, birth);
            System.out.println(customer);

            //
            Blob photo = rs.getBlob("photo");
            InputStream is = photo.getBinaryStream();
            FileOutputStream fos = new FileOutputStream(new File("cat-sql.png"));
            byte[] buffer = new byte[1024];
            int data = 0;
            while ((data = is.read(buffer)) != -1) {
                fos.write(buffer, 0, data);
            }

            JDBCUtils.closeResource(connection, ps, rs);

            fos.close();
            is.close();

        }
    }
}
