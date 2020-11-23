package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式一：继承 Thread 类
 * 多窗口卖票，还存在线程同步（安全）的问题，需要 static
 */

public class WindowTest {
    public static void main(String[] args) {
        Window window1 = new Window("窗口1");
        Window window2 = new Window("窗口2");
        Window window3 = new Window("窗口3");
        window1.start();
        window2.start();
        window3.start();
    }
}

class Window extends Thread {
    // static
    private static int ticket = 100;

    Window(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (ticket > 0) {
                System.out.println(getName() + "：卖票，票号为：" + ticket);
                --ticket;
            } else {
                break;
            }
        }
    }
}