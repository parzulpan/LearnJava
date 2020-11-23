package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式二：实现 Runnable 接口
 * 多窗口卖票，还存在线程同步（安全）的问题，不需要 static
 */

public class WindowTest1 {
    public static void main(String[] args) {
        Window1 window1 = new Window1();
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

class Window1 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
                --ticket;
            } else {
                break;
            }
        }

    }
}