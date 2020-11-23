package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式一：继承 Thread 类
 * 线程的同步 - 同步方法，解决线程同步（安全）的问题
 */

public class WindowTest5 {
    public static void main(String[] args) {
        Window5 window1 = new Window5("窗口1");
        Window5 window2 = new Window5("窗口2");
        Window5 window3 = new Window5("窗口3");
        window1.start();
        window2.start();
        window3.start();
    }
}

class Window5 extends Thread {
    // static
    private static int ticket = 100;

    Window5(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            show();
        }
    }

    private static synchronized void show() {  // 正确，同步监视器为 Window5.class
//    private synchronized void show() {  // 错误，同步监视器为 window1 window2 window3
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
            --ticket;
        }
    }
}
