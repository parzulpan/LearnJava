package java_two;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 生产者消费者模式-传统版本
 */

public class ProducerConsumerTraditional {
    private static final ExecutorService THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(2, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) {
        Share share = new Share();

        // 生产
        THREAD_POOL_EXECUTOR.execute(() -> {
            for (int i = 0; i < 5; i++) {
                share.increment();
            }
        });

        // 消费
        THREAD_POOL_EXECUTOR.execute(() -> {
            for (int i = 0; i < 5; i++) {
                share.decrement();
            }
        });

        THREAD_POOL_EXECUTOR.shutdown();
    }

}

class Share {
    private int number = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            // 判断
            while (number != 0) {
                // 不等于 0，等待
                condition.await();
            }
            // 处理任务
            number++;
            System.out.println(Thread.currentThread().getName() + " 生产 " + number);

            // 通知唤醒其他所有线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            // 判断
            while (number == 0) {
                // 等于 0，等待
                condition.await();
            }
            // 处理任务
            number--;
            System.out.println(Thread.currentThread().getName() + " 消费 " + number);

            // 通知唤醒其他所有线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}