package java_guide;

/**
 * @author parzulpan
 *
 * synchronized 关键字的底层原理 - 同步代码块
 * 使用 javap 查看相关字节码信息
 * java SynchronizedDemo.java
 * javap -c -s -v -l SynchronizedDemo.class
 * 
 */

public class SynchronizedDemo {
    SynchronizedDemo() {
        System.out.println("SynchronizedDemo()");
    }

    public void method() {
        synchronized(SynchronizedDemo.class) {
            System.out.println("synchronized method()");
        }
    }
    
}
