package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式二：实现 Runnable 接口
 * 线程的同步 - 同步代码块，解决线程同步（安全）的问题
 */

public class WindowTest2 {
    public static void main(String[] args) {
        Window2 window2 = new Window2();
        Thread thread1 = new Thread(window2);
        thread1.setName("窗口1");
        Thread thread2 = new Thread(window2);
        thread2.setName("窗口2");
        Thread thread3 = new Thread(window2);
        thread3.setName("窗口3");
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class Window2 implements Runnable {
    private int ticket = 100;
    private final Object object = new Object();

    @Override
    public void run() {
        while (true) {
            // 正确
//            synchronized (this) {
                // 正确
            synchronized (object) {
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
            }
        }
    }
}
