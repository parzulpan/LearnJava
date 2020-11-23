package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc :  使用同步机制将单例模式中懒汉式改写为线程安全的。
 */

public class BankTest {
    public static void main(String[] args) {
        Bank bank1 = Bank.getInstance();
        Bank bank2 = Bank.getInstance();
        System.out.println(bank1 == bank2);

    }
}

class Bank {
    private static Bank instance;

    private Bank(){

    }

//    // 方式一
//    public static synchronized Bank getInstance() {     // 同步监视器为 Bank.class
//        if (instance == null) {
//            instance = new Bank();
//        }
//
//        return instance;
//    }

    // 方式二，效率更高
    public static Bank getInstance() {
        if (instance == null) {
            synchronized (Bank.class) { // 同步监视器为 Bank.class
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }
}