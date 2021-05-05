package java_two;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 可重入锁
 */

public class ReentrantLockDemo {
    public static void main(String[] args) {
//        runInfo();
        runInfo2();
    }

    public static void runInfo() {
        Info info = new Info();
        new Thread(() -> info.getInfo(), "t1").start();
        new Thread(() -> info.getInfo(), "t2").start();
    }

    public static void runInfo2() {
        Info2 info2 = new Info2();
        new Thread(info2, "t3").start();
        new Thread(info2, "t4").start();
    }
}

class Info {
    public synchronized void getInfo() {
        System.out.println(Thread.currentThread().getName() + " invoked getInfo()");
        getInfoName();
    }

    public synchronized void getInfoName() {
        System.out.println(Thread.currentThread().getName() + " invoked getInfoName()");
    }
}

class Info2 implements Runnable {
    Lock lock = new ReentrantLock();

    public void getInfo() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked getInfo()");
            getInfoName();
        } finally {
            lock.unlock();
        }
    }

    public void getInfoName() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked getInfoName()");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        getInfo();
    }
}