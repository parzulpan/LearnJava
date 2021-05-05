package java_two;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 自旋锁
 */

public class SpinLockDemo {
    /** 原子引用线程 */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        // 启动 t1 线程，开始操作
        new Thread(() -> {
            // 开始占有锁
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 开始释放锁
            spinLockDemo.myUnLock();

        }, "t1").start();

        // 让 main 线程暂停1秒，使得 t1 线程先执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 1 秒后，启动 t2 线程，开始占用这个锁
        new Thread(() -> {
            // 开始占有锁
            spinLockDemo.myLock();
            // 开始释放锁
            spinLockDemo.myUnLock();
        }, "t2").start();
    }

    public void myLock() {
        // 获取当前进来的线程
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in");
        while (!atomicReference.compareAndSet(null, thread)) {
            // 开始自旋，期望值是 null，更新值是当前线程，如果是 null，则更新为当前线程，否则自旋
        }
        System.out.println(thread.getName() + " come out");
    }

    public void myUnLock() {
        // 获取当前进来的线程
        Thread thread = Thread.currentThread();
        // 使用完后，将其原子引用变为 null
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " invoked myUnLock()");
    }
}
