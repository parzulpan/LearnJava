package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式一：继承 Thread 类
 * 创建两个子线程，让其中一个线程输出1-1000之间的偶数，另一个线程输出1-1000之间的奇数。
 */

public class ThreadTest {
    public static void main(String[] args) {
        MyThread myThread = new MyThread("偶数线程",0);
        MyThread myThread1 = new MyThread("奇数线程",1);
        myThread.start();
        myThread1.start();

        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println("主线程"  + Thread.currentThread().getName() + " " + i);
            }
        }
    }
}


class MyThread extends Thread {
    private int flag;
    private String msg;

    public MyThread(String msg, int flag) {
        super();
        this.msg = msg;
        this.flag = flag;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == flag) {
                System.out.println(msg + Thread.currentThread().getName()  + " " + i);
            }
        }
    }
}