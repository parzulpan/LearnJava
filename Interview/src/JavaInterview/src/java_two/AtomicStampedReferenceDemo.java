package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 时间戳原子引用，解决 ABA 问题
 */

public class AtomicStampedReferenceDemo {
    public static ExecutorService executorService = Executors.newFixedThreadPool(2);
    public static void main(String[] args) {
        User cc = new User("CC", 23);
        User dd = new User("DD", 24);
        User ee = new User("EE", 25);
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference<>(cc, 1);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // true
                System.out.println(atomicStampedReference.compareAndSet(cc,
                        dd,
                        atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1));
                // true
                System.out.println(atomicStampedReference.compareAndSet(dd,
                        cc,
                        atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1));
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                // 保证完成一次 ABA
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // false
                System.out.println(atomicStampedReference.compareAndSet(cc,
                        ee,
                        stamp,
                        stamp + 1));
            }
        });

        executorService.shutdown();

    }
}
