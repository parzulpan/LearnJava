package java_two;

import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc  CyclicBarrier 召唤神龙例子
 */

public class CyclicBarrierDemo {
    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(7, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        // 定义一个循环屏障，参数1 为需要累加的值，参数2 为需要执行的方法
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙");
        });
        for (int i = 0; i < 7; i++) {
            final int tempInt = i;
            threadPoolExecutor.execute(() -> {
                System.out.println("ThreadName: " + Thread.currentThread().getName() + "，收集到第 " + tempInt + " 颗龙珠");
                try {
                    // 先到的被阻塞，等全部线程完成后，才能执行方法
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
}
