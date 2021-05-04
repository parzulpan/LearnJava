package java_two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-05
 * @project JavaInterview
 * @package java_two
 * @desc volatile 保证可见性说明
 */

public class VolatileDemo1 {
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        MyData myData = new MyData();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " come in");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myData.addTo60();
                System.out.println(Thread.currentThread().getName() + " update number value is " + myData.number);
            }
        });

        while (myData.number == 0) { }

        System.out.println(Thread.currentThread().getName() + " end");
        executorService.shutdown();
    }

}

class MyData {
    /** 不用 volatile 修饰，main 线程一直卡住 */
    volatile int number = 0;
//    int number = 0;

    public void addTo60() {
        this.number = 60;
    }
}
