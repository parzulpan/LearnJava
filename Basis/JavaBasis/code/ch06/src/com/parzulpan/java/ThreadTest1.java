package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式二：实现 Runnable 接口
 */

public class ThreadTest1 {
    public static void main(String[] args) {
        // 3.
        MyThreadA myThreadA = new MyThreadA();
        // 4.
        Thread thread1 = new Thread(myThreadA);
        // 5.
        thread1.setName("线程1");
        thread1.start();

        Thread thread2 = new Thread(myThreadA);
        // 5.
        thread2.setName("线程2");
        thread2.start();
    }
}

// 1.
class MyThreadA implements Runnable {
    // 2.
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName()  + ": " + i);
            }
        }
    }
}