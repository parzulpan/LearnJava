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

class Singleton4Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton4> callable = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Singleton4> future1 = executorService.submit(callable);
        Future<Singleton4> future2 = executorService.submit(callable);
        Singleton4 s1 = future1.get();
        Singleton4 s2 = future2.get();
        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        executorService.shutdown();
    }
}