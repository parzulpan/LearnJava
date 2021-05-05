package java_two;

import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc CountDownLatch 教室关门例子
 */

public class CountDownLatchDemo {
    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(6, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println("ThreadName: " + Thread.currentThread().getName() + "，离开教室");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("ThreadName: " + Thread.currentThread().getName() + "，学生全部离开，已关闭教室");
        threadPoolExecutor.shutdown();
    }
}
