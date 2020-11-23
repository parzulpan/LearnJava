package com.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 线程通信的例子：使用两个线程打印 1-100。线程1, 线程2 交替打印
 */

public class CommunicationTest {
    public static void main(String[] args) {
        Communication communication = new Communication();
        Thread thread1 = new Thread(communication);
        Thread thread2 = new Thread(communication);
        thread1.setName("线程1");
        thread2.setName("线程2");
        thread1.start();
        thread2.start();
    }
}

class Communication implements Runnable {
    private int number = 1;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                notify();

                if (number <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + number++);
                } else {
                    break;
                }

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
