package com.parzulpan.exer;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 银行有一个账户。
 * 银行有一个账户。有两个储户分别向同一个账户存3000元，每次存1000，存3次。每次存完打印账户余额。
 * 分析：
 * 1. 是否是多线程问题？ 是，两个储户线程
 * 2. 是否有共享数据？ 有，账户（账户余额）
 * 3. 该程序是否有安全问题，如果有，如何解决？ 有，使用同步机制，有三种方式。
 */

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account(0);

//        Customer customer1 = new Customer(account);
//        Customer customer2 = new Customer(account);
//        customer1.setName("甲");
//        customer2.setName("乙");
//        customer1.start();
//        customer2.start();

        CustomerA customerA1 = new CustomerA(account);
        Thread thread1 = new Thread(customerA1);
        Thread thread2 = new Thread(customerA1);
        thread1.setName("甲");
        thread2.setName("乙");
        thread1.start();
        thread2.start();
    }
}


class Account {
    private double balance;
    private static final ReentrantLock lock = new ReentrantLock(true);  // 如果有多个 Account，这里的 lock 最好为 static

    public Account(double balance) {
        this.balance = balance;
    }

    // 存钱，线程不同步（安全）
    public void deposit(double amt) {
        if (amt > 0) {
            this.balance += amt;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ": 存钱成功。余额为：" + this.balance);
        }
    }

    // 线程的同步 - 同步代码块，解决线程同步（安全）的问题
    public void deposit1(double amt) {
        synchronized (this) {   //  同步监视器为 this
            if (amt > 0) {
                this.balance += amt;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ": 存钱成功。余额为：" + this.balance);
            }
        }

    }

    // 线程的同步 - 同步方法，解决线程同步（安全）的问题
    public synchronized void deposit2(double amt) {     //  同步监视器为 this，此时为一个 Account。
                                                        // 注意：在继承 Thread 类创建多线程时，要慎用 this
        if (amt > 0) {
            this.balance += amt;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ": 存钱成功。余额为：" + this.balance);
        }
    }

    // 线程的同步 - Lock，解决线程同步（安全）的问题
    public void deposit3(double amt) {
        lock.lock();
        try {
            if (amt > 0) {
                this.balance += amt;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ": 存钱成功。余额为：" + this.balance);
            }
        } finally {
            lock.unlock();
        }
    }
}

// 多线程的创建方式一：继承 Thread 类
class Customer extends Thread {
    private Account account;

    public Customer(Account account) {
        this.account = account;
    }

    @Override
    public void run() {

        for (int i = 0; i < 3; i++) {
//            account.deposit(1000);
//            account.deposit1(1000);
//            account.deposit2(1000);
            account.deposit3(1000);
        }
    }
}

// 多线程的创建方式二：实现 Runnable 接口
class CustomerA implements Runnable {
    private Account account;

    public CustomerA(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
//            account.deposit(1000);
//            account.deposit1(1000);
//            account.deposit2(1000);
            account.deposit3(1000);
        }
    }
}