package java_two;

import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc Semaphore 抢车位例子
 */

public class SemaphoreDemo {
    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(6, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        // 初始化一个信号量为 3，非公平锁，模拟3个停车位
        Semaphore semaphore = new Semaphore(3, false);
        for (int i = 0; i < 6; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("ThreadName: " + Thread.currentThread().getName() + "，抢到车位 ");
                    // 停车 3s
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("ThreadName: " + Thread.currentThread().getName() + "，离开车位 ");
                    semaphore.release();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
}
