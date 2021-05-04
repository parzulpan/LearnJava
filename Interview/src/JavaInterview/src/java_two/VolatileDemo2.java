package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc volatile 不保证原子性说明
 */

public class VolatileDemo2 {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        MyData2 myData2 = new MyData2();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    for (int i = 0; i < 1000; i++) {
                        myData2.addPlusPlus();
                    }
                }
            });
        }
        executorService.shutdown();
        // 等待上面的 10 个线程都计算完成
        // 默认是有两个线程的，一个 main 线程，一个 gc 线程
        while (Thread.activeCount() > 2) { }
        // 可以发现最后的结果总是小于 10000
        System.out.println(Thread.currentThread().getName() + " finally number value is " + myData2.number);
    }
}

class MyData2 {
    AtomicInteger number = new AtomicInteger();
    public void addPlusPlus() {
        number.getAndIncrement();
    }
}