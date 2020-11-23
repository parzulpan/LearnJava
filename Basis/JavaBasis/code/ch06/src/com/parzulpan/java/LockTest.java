package com.parzulpan.java;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 方法二：Lock 锁，JDK5新增
 */

public class LockTest {
    public static void main(String[] args) {
        Window6 window1 = new Window6();
        Thread thread1 = new Thread(window1);
        thread1.setName("窗口1");
        Thread thread2 = new Thread(window1);
        thread2.setName("窗口2");
        Thread thread3 = new Thread(window1);
        thread3.setName("窗口3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class Window6 implements Runnable {
    private int ticket = 100;
    private final ReentrantLock lock = new ReentrantLock(true); // fair 为 true 代表公平锁，为 false 代表不公平锁，默认为 false

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
                    --ticket;
                } else {
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}