package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc 原子引用
 */

public class AtomicReferenceDemo {
    public static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        User aa = new User("AA", 23);
        User bb = new User("BB", 24);
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(aa);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // true
                System.out.println(atomicReference.compareAndSet(aa, bb));
                // true
                System.out.println(atomicReference.compareAndSet(bb, aa));
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // 保证完成一次 ABA
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // true，但是业务要求它返回 false
                System.out.println(atomicReference.compareAndSet(aa, bb));
            }
        });

        executorService.shutdown();
    }
}

class User {
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
