package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式二：实现 Runnable 接口
 * 线程的同步 - 同步方法，解决线程同步（安全）的问题
 */

public class WindowTest4 {
    public static void main(String[] args) {
        Window4 window4 = new Window4();
        Thread thread1 = new Thread(window4);
        thread1.setName("窗口1");
        Thread thread2 = new Thread(window4);
        thread2.setName("窗口2");
        Thread thread3 = new Thread(window4);
        thread3.setName("窗口3");
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class Window4 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {    //  同步监视器为 this
        while (true) {
            show();
        }
    }

    private synchronized void show() {
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
