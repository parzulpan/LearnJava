package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式一：继承 Thread 类
 * 线程的同步 - 同步代码块，解决线程同步（安全）的问题
 */

public class WindowTest3 {
    public static void main(String[] args) {
        Window3 window1 = new Window3("窗口1");
        Window3 window2 = new Window3("窗口2");
        Window3 window3 = new Window3("窗口3");
        window1.start();
        window2.start();
        window3.start();
    }
}

class Window3 extends Thread {
    // static
    private static int ticket = 100;
    private static Object object = new Object();

    Window3(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            // 错误，因为 this 代表 window1 window2 window3 三个对象
//            synchronized (this) {
            // 正确，因为 Class clazz = Window3.class，Window3 只会加载一次
            synchronized (Window3.class) {
            // 正确
//            synchronized (object) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getName() + "：卖票，票号为：" + ticket);
                    --ticket;
                } else {
                    break;
                }
            }

        }
    }
}
