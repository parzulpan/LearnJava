package java_one.two;

/**
 * @author parzulpan
 * @version 1.0
 * @date 2021-04
 * @project JavaInterview
 * @package java_one
 * @desc 2.单例设计模式
 * 懒汉式 - 线程安全，适用于多线程
 */

public class Singleton5 {
    /** 为了避免初始化操作的指令重排序，给 instance 加上了 volatile */
    private static volatile Singleton5 instance;
    private Singleton5() { }
    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized(Singleton5.class) {
                if (instance == null) {
                    // ...
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }
}
