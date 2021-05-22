package java_guide;

/**
 * @author parzulpan
 *
 * synchronized 关键字的底层原理 - 同步方法
 */

public class SynchronizedDemo2 {
    SynchronizedDemo2() {
        System.out.println("SynchronizedDemo2()");
    }

    public synchronized void method() {
        System.out.println("synchronized method()");
    }    
}
