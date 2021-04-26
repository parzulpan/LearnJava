package java_one.two;


import java.util.concurrent.*;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc
 */

class Singleton6Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton6> callable = Singleton6::getInstance;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Singleton6> future1 = executorService.submit(callable);
        Future<Singleton6> future2 = executorService.submit(callable);
        Singleton6 s1 = future1.get();
        Singleton6 s2 = future2.get();
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        executorService.shutdown();
    }
}