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
