package java_two;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc BlockingQueue
 */

public class BlockingQueueDemo {
    static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) {
//        test1();
//        test2();
        try {
            test3();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            test4();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /** 抛出异常 */
    public static void test1() {
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        try {
            // java.lang.IllegalStateException: Queue full
            System.out.println(blockingQueue.add("x"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        try {
            // java.util.NoSuchElementException
            System.out.println(blockingQueue.remove());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // java.util.NoSuchElementException
            System.out.println(blockingQueue.element());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 返回布尔 */
    public static void test2() {
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        System.out.println(blockingQueue.offer("d"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println(blockingQueue.poll());
    }

    /** 阻塞 */
    public static void test3() throws InterruptedException {
        new Thread(() -> {
            try {
                blockingQueue.put("a");
                blockingQueue.put("b");
                blockingQueue.put("c");

                System.out.println(Thread.currentThread().getName() + " start...");
                // 将会阻塞，直到 take
                blockingQueue.put("d");
                System.out.println(Thread.currentThread().getName() + " end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        TimeUnit.SECONDS.sleep(2);

        try {
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();

            System.out.println(Thread.currentThread().getName() + " start...");
            // 将会阻塞
            blockingQueue.take();
            System.out.println(Thread.currentThread().getName() + " end...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /** 超时 */
    public static void test4() throws InterruptedException {
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));

        System.out.println(blockingQueue.offer("d", 2L, TimeUnit.SECONDS));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
    }

}
