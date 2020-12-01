package cn.parzulpan.jdbc.bean;

/**
 * @Author : parzulpan
 * @Time : 2020-12-01
 * @Desc : 事务隔离级别，对应 user_table 表
 */

public class UserTable {
    private String user;
    private String password;
    private int balance;

    public UserTable() {
    }

    public UserTable(String user, String password, int balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserTable{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
