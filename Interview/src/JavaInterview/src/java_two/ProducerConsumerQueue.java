package java_two;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 生产者消费者模式-阻塞队列版本
 */

public class ProducerConsumerQueue {
    private static final ExecutorService THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(2, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) {
        Resource resource = new Resource(new ArrayBlockingQueue<>(10));
        THREAD_POOL_EXECUTOR.execute(resource::increment);

        THREAD_POOL_EXECUTOR.execute(resource::decrement);

        // 5 秒后停止生产和消费
        try {
            TimeUnit.SECONDS.sleep(5);
            resource.stop();
            System.out.println(Thread.currentThread().getName() + " 停止生产和消费");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        THREAD_POOL_EXECUTOR.shutdown();

    }
}

class Resource {
    private volatile boolean flag = true;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue;

    public Resource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void increment() {
        String data;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";

            // 2s 插入一个数据
            try {
                boolean offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + " 生产成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 生产失败");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " 停止生产");
    }

    public void decrement() {
        while (flag) {
            // 2s 移除一个数据
            String poll;
            try {
                poll = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if (poll != null && !"".equals(poll)) {
                    System.out.println(Thread.currentThread().getName() + " 消费成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 消费失败");
                    flag = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
       flag = false;
    }
}
