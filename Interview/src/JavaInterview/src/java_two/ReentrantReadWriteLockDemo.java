package java_two;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 读写锁
 */

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        // 线程操作资源类，5 个线程写
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt +  "");
            }, String.valueOf(i)).start();
        }

        // 线程操作资源类， 5 个线程读
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    /** 可以看到有些线程读取到 null，可用 ReentrantReadWriteLock 解决 */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String k, Object v) {
        // 创建一个写锁
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入");
            try {
                // 模拟网络拥堵，延迟 0.3s
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(k, v);
            System.out.println(Thread.currentThread().getName() + " 写入完成");
        } finally {
            lock.writeLock().unlock();
        }

    }

    public void get(String k) {
        // 创建一个读锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取");
            try {
                // 模拟网络拥堵，延迟 0.3s
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = map.get(k);
            System.out.println(Thread.currentThread().getName() + " 读取完成 " + o);
        } finally {
            lock.readLock().unlock();
        }
    }
}