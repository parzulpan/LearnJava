package com.parzulpan.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 多线程的创建方式三：实现 Callable 接口
 */

public class NumberThreadTest {
    public static void main(String[] args) {
        // 3. 创建 Callable 接口实现类的对象
        NumberThread numberThread = new NumberThread();

        // 4. 将此 Callable 接口实现类的对象传递到 FutureTask 构造器中，创建 FutureTask 对象
//        FutureTask futureTask = new FutureTask(numberThread);
        FutureTask<Integer> futureTask = new FutureTask<Integer>(numberThread);

        // 5. 将 FutureTask 对象传递到 Thread 构造器中，创建 Thread 对象，并 start() 启动
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            // 6. 获取 Callable 中 call 方法的返回值
            // get() 返回值 即为 FutureTask 构造器参数 Callable 实现类重写的 call() 返回值
//            Object sum = futureTask.get();
            Integer sum = futureTask.get();

            System.out.println("偶数和为：" + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

// 1. 创建一个实现 Callable 的实现类
class NumberThread implements Callable<Integer> {

    // 2. 实现 call() ，将此线程需要执行的操作声明在 call() 中
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
                sum += i;
            }
        }

        return sum;
    }
}