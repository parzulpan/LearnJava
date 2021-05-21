package java_two;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author parzulpan
 *
 * 虚引用
 * VM options: -Xms5m -Xmx5m -XX:+PrintGCDetails
 */

public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o, referenceQueue);
        System.out.println(o);
        // null
        System.out.println(phantomReference.get());
        // null
        System.out.println(referenceQueue.poll());
        System.out.println("\n---\n");
        o = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(o);
        // null
        System.out.println(phantomReference.get());
        // java.lang.ref.PhantomReference@4554617c
        System.out.println(referenceQueue.poll());
    }
}
